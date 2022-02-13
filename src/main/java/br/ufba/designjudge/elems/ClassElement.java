package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import br.ufba.designjudge.exception.JudgeException;

public class ClassElement extends Element {
	private Optional<Class> klass;
	
	public ClassElement(String name) {
		super(name);
		klass = getReflectionElement();
	}
	
	
	public ClassElement getSuperclass() {
		ClassElement newClass = new ClassElement("");
		Class superClass = getReflectionElement().get().getSuperclass();
		newClass.loadFromReflectionElement(superClass);
		return newClass;
	}
	
	// TODO: should return ElementSet
	// lazy evaluation; returns element even if it does not exist
	@Override
	public Element get(Element element) {
		if (element instanceof FieldElement) {
			FieldElement field = (FieldElement)element;
			field.setKlass(this);
			return field;
		} else if (element instanceof ConstructorElement) {
			ConstructorElement ctor = (ConstructorElement)element;
			ctor.setKlass(this);
			return ctor;
		} else if (element instanceof MethodElement) {
			MethodElement m = (MethodElement)element;
			m.setKlass(this);
			return m;
		} else if (element instanceof ElementSet) {
			return super.get(element);
		} else {
			throw new JudgeException("not implemented yet");
		}
	}
	
	@Override
	public ElementSet getAll(ElementSet set) {
		ElementSet result = new ElementSet();
		
		set.stream().forEach(element -> {
			if (element instanceof ClassMemberElement) {
				ClassMemberElement cmElement = (ClassMemberElement)element;
				cmElement.setKlass(this);
				Object[] rElems = cmElement.getMatchingReflectionElements();
				for (Object rElem : rElems) {
					if (rElem instanceof Field) {
						result.add(new FieldElement((Field)rElem));
					} else if (rElem instanceof Method) {
						result.add(new MethodElement((Method)rElem));
					} else if (rElem instanceof Constructor) {
						result.add(new ConstructorElement((Constructor)rElem));
					}
				}
			}
		});
			
		return result;
	}
	
	// TODO: why it does not work with get all
	@Override
	public boolean has(Element element) {
		if (klass == null) {
			return false;
		}
		
//		return !getAll(new ElementSet(element)).isEmpty();
		if (element instanceof MethodElement) {
			Stream<Method> methods = methodsWithName(element.getName());
			return methods.anyMatch(m -> m.getName().equals(element.getName()));
		} else if (element instanceof FieldElement) {
			return get(element) instanceof FieldElement;
		} else if (element instanceof ConstructorElement) {
			return !getAll(new ElementSet(element)).isEmpty();
		} else if (element instanceof ElementSet) {
			return super.has(element);
		} else {
			throw new JudgeException("Not implemented: class.get ...");
		}
	}
	
	public Optional<Class> getReflectionElement() {
		try {
			return Optional.of(Class.forName(getName()));
		} catch (ClassNotFoundException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public Object[] getMatchingReflectionElements() {
		Optional<Class> c = getReflectionElement();
		if (c.isEmpty()) {
			return new Object[0];
		} else {			
			return new Object[] { c.get() };
		}
	}

	// TODO: convert to MethodElement
	private Stream<Method> methodsWithName(String name) {
		Method[] result;
		if (klass.isPresent()) {
			result = klass.get().getDeclaredMethods();
		} else {
			result = new Method[0];
		}
		return Arrays.stream(result);
	}

	public Object instantiate(Object ...params) {
		ConstructorElement ctor = new ConstructorElement();
		ctor.setKlass(this);
		return ctor.call(params);
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Class c = (Class)elem;
		setModifiers(c.getModifiers());
		name = c.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassElement other = (ClassElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}