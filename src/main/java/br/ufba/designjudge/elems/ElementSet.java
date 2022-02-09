package br.ufba.designjudge.elems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ElementSet extends Element {
	private ArrayList<Element> elements = new ArrayList<>();
	
	// TODO: Create a custom element-type, including set etc.
	public ElementSet() {
		super(null);
	}
	
	public ElementSet(Element e) {
		super(null);
		elements.add(e);
	}
	
	public Stream<Element> stream() {
		return elements.stream();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	public void add(Element e) {
		elements.add(e);
	}
	
	public ElementSet(Element[] elementArray) {
		this();
		elements.addAll(Arrays.asList(elementArray));
	}
	
	public int count() {
		return elements.size();
	}
	
	public boolean anyHasAll(Element element) {
		return elements.stream().anyMatch(e -> e.has(element));
	}

	public boolean allHaveAll(Element element) {
		return elements.stream().allMatch(e -> e.has(element));
	}
	
	public boolean allHave(Element element) {
		return allHaveAll(element);
	}
	
	@Override
	public boolean has(Element element) {
		return anyHasAll(element);
	}

	// all have modifier
	@Override
	public boolean hasModifier(int modifierFlag) {
		return elements.stream().allMatch(e -> e.hasModifier(modifierFlag));
	}
	
	/**
	 * Return first non-null element
	 */
	@Override
	public Element get(Element element) {
		for (Element e : elements) {
			Element result = e.get(element);
			if (!(e instanceof NullElement)) {
				return result;
			}
		}
		return super.get(element);
	}

	@Override
	public Object[] getMatchingReflectionElements() {
		ArrayList<Object> list = new ArrayList<>();
		for (Element element : elements) {
			list.addAll(Arrays.asList(element.getMatchingReflectionElements()));
		}
		return list.toArray();
	}
	
	public void print() {
		for (Element element : elements) {
			System.out.println(element);
		}
	}
	
	@Override
	public void loadFromReflectionElement(Object elem) {
		// do nothing
	}

	@Override
	public ElementSet getAll(ElementSet set) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public String toString() {
		return "ElementSet[" + String.join(", ", elements.stream().map(x -> x.toString()).toArray(String[]::new)) + "]";
		
	}
}
