package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program za prikaz fraktala temeljenog na Newton-Raphson iteraciji.
 * Fraktal se crta slijedno, koristeći samo jednu dretvu.
 * 
 * @author mskrabic
 *
 */
public class Newton {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int idx = 1;
		String line;
		List<Complex> rootList = new ArrayList<>();
		while (true) {
			System.out.print("Root " + idx + ">");
			line = sc.nextLine();
			if (line.equals("done"))
				break;			
			rootList.add(Complex.parse(line));
			idx++;
		}
		sc.close();
		System.out.println("Image of the fractal will appear shortly. Thank you.");
		
		Complex[] roots = new Complex[rootList.size()];
		for (int i = 0; i < roots.length; i++) {
			roots[i] = rootList.get(i);
		}
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
		FractalViewer.show(new NewtonProducer(crp));
		
	}

	
	/**
	 * Razred predstavlja implementaciju sučelja {@link IFractalProducer} za Newton-Raphson iteraciju.
	 * 
	 * @author mskrabic
	 *
	 */
	public static class NewtonProducer implements IFractalProducer {
		/**
		 * Polinom za koji se crta fraktal u obliku f(z)=z0*(z-z1)*...*(z-zn).
		 */
		private ComplexRootedPolynomial crp;
		
		/**
		 * Polinom za koji se crta fraktal u obliku f(z)=z0+z1*z+..+zn*z^n.
		 */
		private ComplexPolynomial cp;
		
		/**
		 * Granica konvergencije.
		 */
		private static final double CONVERGENCE_TRESHOLD = 0.001;
		
		/**
		 * Granica udaljenosti od korijena polinoma.
		 */
		private static final double ROOT_TRESHOLD = 0.002;
		
		/**
		 * Konstruktor koji inicijalizira {@link NewtonProducer}-a za predani polinom.
		 * 
		 * @param crp {@link ComplexRootedPolynomial} za koji se želi nacrtati fraktal.
		 */
		public NewtonProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			this.cp = crp.toComplexPolynom();
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			
			int offset = 0;
			short[] data = new short[width * height];
			
			for(int y = 0; y < height; y++) {
				 for(int x = 0; x < width; x++) {
					 Complex zn = mapToComplexPlain(x, y, width, height, reMin, reMax, imMin, imMax);
					 double module;
					 int iter = 0;
					 do {
						 Complex numerator = crp.apply(zn);
						 Complex denominator = cp.derive().apply(zn);
						 Complex fraction = numerator.divide(denominator);
						 Complex znold = zn;	 
						 zn = zn.sub(fraction);
						 module = zn.sub(znold).module();
					 } while(module > CONVERGENCE_TRESHOLD && iter < cp.order());
					 int index = crp.indexOfClosestRootFor(zn, ROOT_TRESHOLD);
					 data[offset++]= (short)(index+1);
				 }
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order() + 1), requestNo);
		}

		/**
		 * Metoda računa kompleksni broj koji se nalazi na koordinatama (x,y) prozora za prikaz fraktala.
		 * 
		 * @param x x-koordinata točke na prozoru za prikaz fraktala. 
		 * @param y y-koordinata točke na prozoru za prikaz fraktala.
		 * @param width širina prozora.
		 * @param height visina prozora.
		 * @param reMin minimalna vrijednost apcise.
		 * @param reMax maksimalna vrijednost apcise.
		 * @param imMin minimalna vrijednost ordinate.
		 * @param imMax maksimalna vrijednost ordinate.
		 * 
		 * @return odgovarajući kompleksni broj.
		 */
		private Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax,
				double imMin, double imMax) {
			
			double re = ((double)x/(width-1))*(reMax-reMin) + reMin;
			double im = ((double)(height-1-y)/(height-1))*(imMax-imMin) + imMin;
			
			return new Complex(re, im);
		}
	}

}
