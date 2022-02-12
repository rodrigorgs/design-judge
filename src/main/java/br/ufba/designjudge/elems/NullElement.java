package br.ufba.designjudge.elems;

import br.ufba.designjudge.exception.JudgeException;

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
		throw new JudgeException("Method " + getName() + " not found.");
	}

	@Override
	public ClassElement asClass() {
		throw new JudgeException("Class " + getName() + " not found.");
	}

	@Override
	public FieldElement asField() {
		throw new JudgeException("Field " + getName() + " not found.");
	}
	
	@Override
	public ConstructorElement asConstructor() {
		throw new JudgeException("Constructor " + getName() + " not found.");
	}

}
