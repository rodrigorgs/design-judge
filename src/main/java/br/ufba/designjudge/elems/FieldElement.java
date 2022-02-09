package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class FieldElement extends ClassMemberElement {
	private int modifiers;

	
	public FieldElement() {
		super(null);
	}
	public FieldElement(String name) {
		super(name);
	}

	public FieldElement(Field field) {
		super(field.getName());
		loadFromReflectionElement(field);
	}
	
	@Override
	public Object[] getMatchingReflectionElements() {
		ArrayList<Constructor> result = new ArrayList<>();

		Class c = getReflectionClass();
		if (c == null) {
			return new Object[0];
		}

		if (getName() == null) {
			return c.getDeclaredFields();
		} else {
			try {
				return new Object[] { c.getDeclaredField(getName()) };
			} catch (NoSuchFieldException | SecurityException e) {
				return new Object[0];
			}
		}
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Field c = (Field)elem;
		setModifiers(c.getModifiers());
		// TODO: type
	}

}
