package br.ufba.designjudge;


import org.junit.Test;

import br.ufba.designjudge.elems.ClassElement;
import br.ufba.designjudge.elems.ElementSet;

import static org.junit.Assert.*;
import static br.ufba.designjudge.DesignJudge.*;

public class Tests {
	ClassElement aClass = klass("br.ufba.designjudge.sample.ClassA");
	
	@Test
	public void classExists() {
		aClass.mustExist();
	}
	
	@Test(expected = RuntimeException.class)
	public void classDoesNotExist() {
		klass("x.y.z").mustExist();
	}
	
	@Test
	public void getAllFields() {
		ElementSet res = aClass.getAll(field());
		aClass.getAll(field()).print();
		assertEquals(3, res.size());
	}
	
	// TODO: field() should return ElementSet
	@Test
	public void getFieldByName() {
		ElementSet res = aClass.getAll(fields("publicField"));
		assertEquals(1, res.size());
	}
	
	@Test
	public void getInexistingField() {
		ElementSet res = aClass.getAll(fields("oiqweoqijwe"));
		assertEquals(0, res.size());
	}
	
	// TODO: create mustNotExist() or must(not(exist()))
	@Test(expected = RuntimeException.class)
	public void getFieldMustExist() {
		aClass.getAll(fields("oiqweoqijwe")).mustExist();
	}

	
}
