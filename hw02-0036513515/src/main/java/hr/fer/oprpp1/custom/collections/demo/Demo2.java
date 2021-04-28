package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
//import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class Demo2 {

	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		//Collection col2 = new LinkedListIndexedCollection(col1);
		ElementsGetter getter1 = col1.createElementsGetter();
		//ElementsGetter getter2 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		//col1.clear();
		System.out.println(getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		}
}
