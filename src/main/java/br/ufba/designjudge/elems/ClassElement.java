package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class ClassElement extends Element {
	private Class klass;
	
	public ClassElement(String name) {
		super(name);
		
		klass = getReflectionElement();
	}
	
	// TODO: should return ElementSet
	@Override
	public Element get(Element element) {
		if (element instanceof FieldElement) {
			try {
				Field field = klass.getDeclaredField(element.getName());
				return new FieldElement(field);
			} catch (NoSuchFieldException | SecurityException e) {
				return super.get(element);
			}
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
			throw new RuntimeException("not implemented yet");
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
		
		System.out.println("get all: " + getAll(new ElementSet(element)));
//		return !getAll(new ElementSet(element)).isEmpty();
		if (element instanceof MethodElement) {
			Stream<Method> methods = methodsWithName(element.getName());
			System.out.println(methods);
			return methods.anyMatch(m -> m.getName().equals(element.getName()));
		} else if (element instanceof FieldElement) {
			return get(element) instanceof FieldElement;
		} else if (element instanceof ConstructorElement) {
			return !getAll(new ElementSet(element)).isEmpty();
		} else if (element instanceof ElementSet) {
			return super.has(element);
		} else {
			throw new RuntimeException("Not implemented: class.get ...");
		}
	}
	
	public Class getReflectionElement() {
		try {
			return Class.forName(getName());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	@Override
	public Object[] getMatchingReflectionElements() {
		Class c = getReflectionElement();
		if (c == null) {
			return new Object[0];
		} else {			
			return new Object[] { c };
		}
	}

	// TODO: convert to MethodElement
	private Stream<Method> methodsWithName(String name) {
		if (klass == null) {
			return Arrays.stream(new Method[] {});
		} 
		System.out.println("declared: " + klass.getDeclaredMethods());
		return Arrays.stream(klass.getDeclaredMethods());
	}

	@Override
	public void loadFromReflectionElement(Object elem) {
		Class c = (Class)elem;
		setModifiers(c.getModifiers());
	}

	
}
