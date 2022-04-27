package br.ufba.designjudge;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import br.ufba.designjudge.elems.ClassElement;
import br.ufba.designjudge.elems.ConstructorElement;
import br.ufba.designjudge.elems.ElementSet;
import br.ufba.designjudge.elems.EnumElement;
import br.ufba.designjudge.elems.FieldElement;
import br.ufba.designjudge.elems.MethodElement;

public class DesignJudge {
	public static ClassElement klass(String name) {
		return new ClassElement(name);
	}
	
	public static ElementSet klasses(String ...names) {
		ClassElement[] classes = Arrays.stream(names).map(name -> new ClassElement(name)).toArray(ClassElement[]::new);
		return new ElementSet(classes);
	}
	
	public static MethodElement method(String name) {
		return new MethodElement(name);
	}

	public static MethodElement method() {
		return new MethodElement();
	}
	
	public static ElementSet methods(String ...names) {
		MethodElement[] methods = Arrays.stream(names).map(name -> new MethodElement(name)).toArray(MethodElement[]::new);
		return new ElementSet(methods);
	}
	
	public static FieldElement field() {
		return new FieldElement();
	}
	public static FieldElement field(String name) {
		return new FieldElement(name);
	}
	
	public static ElementSet fields(String ...names) {
		FieldElement[] fields = Arrays.stream(names).map(name -> new FieldElement(name)).toArray(FieldElement[]::new);
		return new ElementSet(fields);
	}

	public static ConstructorElement constructor() {
		return new ConstructorElement();
	}
	
	public static EnumElement enumeration(String name) {
		return new EnumElement(name);
	}
}
