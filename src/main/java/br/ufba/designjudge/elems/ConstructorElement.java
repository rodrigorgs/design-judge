package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

public class ConstructorElement extends ClassMemberElement {
	public static final int UNDEFINED_PARAMETER_COUNT = -1;
	private int parameterCount = UNDEFINED_PARAMETER_COUNT;
	
	public ConstructorElement() {
		super(null);
	}
	
	public ConstructorElement(Constructor constructor) {
		super("");
		loadFromReflectionElement(constructor);
	}
	
	public ConstructorElement withParameterCount(int count) {
		parameterCount = count;
		return this;
	}
	
	public int getParameterCount() {
		return parameterCount;
	}
	
	@Override
	public void mustExist() {
		if (getMatchingReflectionElements().length == 0) {
			super.mustExist();
		}
	}
	
	@Override
	public Constructor[] getMatchingReflectionElements() {
		Class c = getReflectionClass();
		if (c == null) {
			return new Constructor[0];
		}
		
		Stream<Constructor> stream = Arrays.stream(c.getDeclaredConstructors());
		
		if (parameterList != null) {
			stream = stream.filter(ctor -> Arrays.equals(ctor.getParameterTypes(), parameterList));
		}
		if (parameterList == null && parameterCount != UNDEFINED_PARAMETER_COUNT) {
			stream = stream.filter(ctor -> ctor.getParameterCount() == parameterCount);
		}
		
		return stream.toArray(Constructor[]::new);
	}
	
	// TODO: if there are multiple matches, use parameter list to choose correct one
	public Object call(Object ...args) {
		Constructor[] constructors = getMatchingReflectionElements();
		
		if (constructors.length == 1) {
			try {
				return constructors[0].newInstance(args);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		} else if (constructors.length > 1) {
			throw new RuntimeException("Multiple constructor matches");
		}
		return null;
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Constructor c = (Constructor)elem;
		setModifiers(c.getModifiers());
		// TODO: parameters
	}

}
