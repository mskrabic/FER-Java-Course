package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred koji se koristi za izračun izraza zadanih u postfix notaciji koristeći <code>ObjectStack</code>
 * razred. Parametri se predaju kao string preko konzole. Ispis se također šalje na konzolu.
 * 
 * @author mskrabic
 *
 */
public class StackDemo {
	
	/**
	 * Metoda koja izvršava traženu operaciju nad elementima stoga.
	 * Podržane operacije su zbroj(+), razlika(-), množenje(*), dijeljenje(/) i mod(%).
	 * 
	 * @param op operacija.
	 * @param first prvi operand.
	 * @param second drugi operand.
	 * 
	 * @return rezultat operacije.
	 * 
	 * @throws UnsupportedOperationException ako se traži nepodržana operacija.
	 */
	public static Object operation(String op, int first, int second) {
		switch (op) {
		case "+":
			return first+second;
		case "-":
			return first-second;
		case "*":
			return first*second;
		case "/":
			return first/second;
		case "%":
			return first%second;
		default:
			throw new UnsupportedOperationException("Allowed operations are: +, -, *, / and %. It was: " + op + ".");
		}
		
	}

	public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack();
		String[] input = args[0].split("\\s+");
		
		for (String s: input) {
			if (s.matches("[-+]?\\d+")) {
				stack.push(s);
			} else {
				Integer second =  Integer.valueOf(stack.pop().toString());
				Integer first = Integer.valueOf(stack.pop().toString());
				Object result = operation(s, first.intValue(), second.intValue());
				stack.push(result);
			}
		}
		if (stack.size() != 1)
			throw new RuntimeException("Evaluation was not successful: " + stack.size() + " elements found on the stack.");
		System.out.println(stack.pop());
	}
}
