package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Demo1 {
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2, 0),
				Complex.ONE,
				Complex.ONE_NEG,
				Complex.IM,
				Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		
		Complex c1 = new Complex(2, 2);
		Complex c2 = new Complex(2, 0);
		
		System.out.println(c1.divide(c2));
		new Complex(1, 0).root(4).forEach(System.out::println);
	}

}
