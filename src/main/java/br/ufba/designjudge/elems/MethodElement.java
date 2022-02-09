package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class MethodElement extends ClassMemberElement {
	public static final int UNDEFINED_PARAMETER_COUNT = -1;
	private int parameterCount = UNDEFINED_PARAMETER_COUNT;
	
	public MethodElement(String name) {
		super(name);
	}
	
	public MethodElement(Method method) {
		super(method.getName());
		loadFromReflectionElement(method);
	}

	public MethodElement withParameterCount(int count) {
		parameterCount = count;
		return this;
	}
	
	public int getParameterCount() {
		return parameterCount;
	}
	
	@Override
	public Object[] getMatchingReflectionElements() {
		ArrayList<Constructor> result = new ArrayList<>();
		
		Class c = getReflectionClass();
		if (c == null) {
			return new Object[0];
		}
		

		Stream<Method> stream = Arrays.stream(c.getDeclaredMethods());
		if (getName() != null) {
			stream = stream.filter(m -> m.getName().equals(getName()));
		}
		if (parameterList != null) {
			stream = stream.filter(m -> Arrays.equals(m.getParameterTypes(), parameterList));
		}
		if (parameterList == null && parameterCount != UNDEFINED_PARAMETER_COUNT) {
			stream = stream.filter(m -> m.getParameterCount() == parameterCount);
		}
		
		return stream.toArray();
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Method c = (Method)elem;
		setModifiers(c.getModifiers());
		// TODO: parameters, return type
	}

}
