package br.ufba.designjudge.elems;

public class NullElement extends Element {
	public NullElement() {
		super("");
	}

	public NullElement(String name) {
		super(name);
	}

	@Override
	public Object[] getMatchingReflectionElements() {
		return new Object[0];
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		// do nothing
	}

	@Override
	public MethodElement asMethod() {
		throw new RuntimeException("Method " + getName() + " not found.");
	}

	@Override
	public ClassElement asClass() {
		throw new RuntimeException("Class " + getName() + " not found.");
	}

	@Override
	public FieldElement asField() {
		throw new RuntimeException("Field " + getName() + " not found.");
	}
	
	@Override
	public ConstructorElement asConstructor() {
		throw new RuntimeException("Constructor " + getName() + " not found.");
	}

}
