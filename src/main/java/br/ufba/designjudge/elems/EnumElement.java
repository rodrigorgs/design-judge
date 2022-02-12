package br.ufba.designjudge.elems;

import java.util.HashMap;
import java.util.Map;

import br.ufba.designjudge.exception.JudgeException;

public class EnumElement extends Element {

	public EnumElement(String name) {
		super(name);
	}

	@Override
	public Class[] getMatchingReflectionElements() {
		try {
			Class c = Class.forName(getName());
			if (c.isEnum()) {
				return new Class[] { c };
			}
		} catch (ClassNotFoundException e) {
		}
		return new Class[0];
	}

	@Override
	public void loadFromReflectionElement(Object elem) {
		Enum e = (Enum)elem;
		// do nothing
		// TODO: should set name? name should be protected?
	}
	
	public Class getMatchingReflectionElementOrThrow() {
		Class[] matching = getMatchingReflectionElements();
		if (matching.length != 0) {
			Class e = matching[0];
			return matching[0];
		} else {
			throw new JudgeException("No matching element found");
		}
	}
	
	public Object getEnumValue(String s) {
		Map<String, Object> map = getNameToValueMap();
		return map.get(s);
	}

	public Map<String, Object> getNameToValueMap() {
		HashMap<String, Object> map = new HashMap<>();

		Class enumeration = getMatchingReflectionElementOrThrow();
    	Object[] constants = enumeration.getEnumConstants();
    	for (Object constant: constants) {
    		Enum value = (Enum)constant;
    		map.put(value.name(), value);
    	}
		
		return map;
	}
}