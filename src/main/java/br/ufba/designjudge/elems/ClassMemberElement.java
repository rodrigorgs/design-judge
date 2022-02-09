package br.ufba.designjudge.elems;

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
        
    public Class getReflectionClass() {
    	if (klass == null) {
			return null;
		}
		
		Class c = klass.getReflectionElement();
		if (c == null) {
			return null;
		}
		
		return c;
    }
    
	ClassElement getKlass() {
		return klass;
	}

	void setKlass(ClassElement klass) {
		this.klass = klass;
	}
}
