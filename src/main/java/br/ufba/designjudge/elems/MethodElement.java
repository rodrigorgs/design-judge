package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
	public Method[] getMatchingReflectionElements() {
		ArrayList<Constructor> result = new ArrayList<>();
		
		Class c = getReflectionClass();
		if (c == null) {
			return new Method[0];
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
		
		return stream.toArray(Method[]::new);
	}
	
	// TODO: if there are multiple matches, use parameter list to choose correct one
	public Object call(Object self, Object ...args) {
		Class c = getReflectionClass();
		if (c == null || getName() == null || getName().isEmpty()) {
			return null;
		}
		Class[] paramClasses = Arrays.stream(args).map(x -> x.getClass()).toArray(Class[]::new);
		try {
			Method method = c.getDeclaredMethod(getName(), paramClasses);
			if (method != null) {
				return method.invoke(self, args);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Method c = (Method)elem;
		setModifiers(c.getModifiers());
		// TODO: parameters, return type
	}

}
