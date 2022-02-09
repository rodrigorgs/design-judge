package br.ufba.designjudge.elems;

public class NullElement extends Element {
	public NullElement() {
		super("");
	}

	@Override
	public Object[] getMatchingReflectionElements() {
		return new Object[0];
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		// do nothing
	}

}
