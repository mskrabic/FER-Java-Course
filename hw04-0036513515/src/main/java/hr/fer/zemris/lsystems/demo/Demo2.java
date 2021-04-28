package hr.fer.zemris.lsystems.demo;

import hr.fer.oprpp1.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.LSystem;

public class Demo2 {
	
	public static void main(String[] args) {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("F").registerProduction('F', "F+F--F+F");
		LSystem sys = builder.build();
		System.out.println(sys.generate(2).equals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F"));
		
	}

}
