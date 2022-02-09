package br.ufba.designjudge.sample;

public class ClassA {
	private String privateField;
	public int publicField;
	protected double protectedField;
	
	public ClassA() {}
	public ClassA(String x) {}
	public ClassA(String x, int y) {}
	
	private void privateMethod() {}
	public int publicMethodWithReturn() { return 0; }
	protected void protectedMethodWithParams(String x, int y) { }
}
