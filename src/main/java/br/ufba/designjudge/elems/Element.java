package br.ufba.designjudge.elems;

import br.ufba.designjudge.exception.JudgeException;

public abstract class Element {
	protected String name;
	private int modifiers;
	
	int getModifiers() {
		return modifiers;
	}

	void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public String getName() {
		return name;
	}

	public Element(String name) {
		super();
		this.name = name;
	}

    public Element withModifiers(int modifiers) {
    	this.modifiers = modifiers;
    	return this;
    }
	
	public abstract Object[] getMatchingReflectionElements();
	
	public Object getSingleMatchingReflectionElement() {
		Object[] elems = getMatchingReflectionElements();
		if (elems.length == 0) {
			return null;
		} else if (elems.length == 1) {
			return elems[0];
		} else {
			throw new JudgeException("More than one match");
		}
	}
	
	// TODO: make it an element set
	// TODO: replace body with exception throwing
	public Element get(Element e) {
		return new NullElement(e.getName());
	}

	public ElementSet getAll(ElementSet set) {
		throw new JudgeException("Not implemented");
	}
	
	public ElementSet getAll(Element elem) {
		return getAll(new ElementSet(elem));
	}

	public abstract void loadFromReflectionElement(Object elem);
	
	public boolean hasModifier(int modifierFlag) {
		Object o = getSingleMatchingReflectionElement();
		if (o != null) {
			loadFromReflectionElement(o);
		}
		return (modifiers & modifierFlag) > 0;
	}
	
	public void mustHaveModifier(int modifierFlag) {
		System.out.println(this + " modifiers: " + modifiers);
		if (!hasModifier(modifierFlag)) {
			throw new JudgeException(this + " does not have modifier " + modifierFlag);
		}
	}
	
	public boolean hasAll(ElementSet elementSet) {
		return elementSet.stream().allMatch(e -> has(e));
	}

	public boolean hasAny(ElementSet elementSet) {
		return elementSet.stream().anyMatch(e -> has(e));
	}
	
	public boolean has(Element element) {
		if (element instanceof ElementSet) {
			return hasAll((ElementSet)element);
		} else {
			return false;
		}
	}

	public boolean has(Element ...elements) {
		ElementSet set = new ElementSet(elements);
		return has(set);
	}
	
	// TODO: maybe must(exist()), must(have(method("size")))
	public void mustExist() {
		Object[] matches = getMatchingReflectionElements();
		if (matches.length == 0) {
			throw new JudgeException(this + " does not exist");
		}
	}
	
	public Element mustHave(Element e) {
		if (!has(e)) {
			throw new JudgeException(this + " does not have element " + e);
		}
		return this;
	}

	public Element mustHave(Element ...elements) {
		ElementSet set = new ElementSet(elements);
		return mustHave(set);
	}
	
	public Element mustNotHave(Element e) {
		if (has(e)) {
			throw new JudgeException(this + " should not have element " + e);
		}
		return this;
	}

	
	
	@Override
	public String toString() {
		return getClass().getName() + "[" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public MethodElement asMethod() {
		return (MethodElement) this;
	}
	
	public ClassElement asClass() {
		return (ClassElement) this;
	}
	
	public FieldElement asField() {
		return (FieldElement) this;
	}
	
	public ConstructorElement asConstructor() {
		return (ConstructorElement) this;
	}
}
