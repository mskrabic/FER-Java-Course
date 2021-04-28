package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Razred koji se koristi za provjeru je li objekt parni integer.
 * 
 * @author mskrabic
 *
 */
public class EvenIntegerTester implements Tester {
	/**
	 * Metoda provjerava je li predani objekt parni integer.
	 */
	 @Override
	 public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
	 }
	 public static void main(String[] args) {
			Tester t = new EvenIntegerTester();
			System.out.println(t.test("Ivo"));
			System.out.println(t.test(22));
			System.out.println(t.test(3));
	}
}


