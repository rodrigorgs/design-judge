package br.ufba.designjudge.elems;

import java.util.Optional;

public abstract class ClassMemberElement extends Element {
	private ClassElement klass;
	protected Class[] parameterList;

    public ClassMemberElement(String name) {
    	super(name);
    }
	
    public ClassMemberElement withClass(ClassElement klass) {
    	this.klass = klass;
    	return this;
    }
    
    // TODO: use mixins (interfaces)
    public ClassMemberElement withParameters(Class ...parameters) {
    	parameterList = parameters.clone();
    	return this;
    }
        
    public Optional<Class> getReflectionClass() {
    	if (klass == null) {
			return Optional.empty();
		} else {
			return klass.getReflectionElement();
		}
    }
    
	ClassElement getKlass() {
		return klass;
	}

	void setKlass(ClassElement klass) {
		this.klass = klass;
	}
}
