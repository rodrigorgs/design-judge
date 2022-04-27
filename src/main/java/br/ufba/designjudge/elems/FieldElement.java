package br.ufba.designjudge.elems;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class FieldElement extends ClassMemberElement {
		
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
	public Field[] getMatchingReflectionElements() {
		ArrayList<Constructor> result = new ArrayList<>();

		Optional<Class> c = getReflectionClass();
		if (c.isEmpty()) {
			return new Field[0];
		}

		Stream<Field> stream = Arrays.stream(c.get().getDeclaredFields());
		stream = stream.filter(f -> !f.getName().startsWith("$"));
		if (getName() != null) {
			stream = stream.filter(f -> f.getName().equals(getName()));
		}

		return stream.toArray(Field[]::new);
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		Field c = (Field)elem;
		setModifiers(c.getModifiers());
		// TODO: type
	}

}
