package hr.fer.oprpp1.hw01;

/**
 * Razred predstavlja model kompleksnog broja.
 * 
 * @author mskrabic
 *
 */
public class ComplexNumber {

	/**
	 * Realni dio kompleksnog broja.
	 */
	private double real;
	
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	private double imaginary;

	/**
	 * Konstruktor koji inicijalizira novi kompleksni broj s predanim vrijednostima realnog i 
	 * imaginarnog dijela.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Metoda stvara kompleksni broj iz predanog realnog broja.
	 * 
	 * @param real realni broj koji se želi zapisati kao kompleksni.
	 * 
	 * @return novi kompleksni broj s realnim dijelom jednakim predanom parametru.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Metoda stvara kompleksni broj iz predanog imaginarnog broja.
	 * 
	 * @param imaginary imaginarni broj koji se želi zapisati kao kompleksni.
	 * 
	 * @return novi kompleksni broj s imaginarnim dijelom jednakim predanom parametru.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Metoda stvara novi kompleksni broj iz predanih polarnih koordinata.
	 * 
	 * @param magnitude modul kompleksnog broja.
	 * @param angle kut kompleksnog broja.
	 * 
	 * @return novi kompleksni broj sa zadanim koordinatama.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {	
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	
	/**
	 * Metoda parsira predani string u kompleksni broj. String mora odgovarati formatu kompleksnog broja
	 * u Kartezijevim koordinatama "a+bi", pri čemu se i realni i imaginarni dio mogu izostaviti.
	 * 
	 * @param s String koji predstavlja kompleksni broj.
	 * 
	 * @return novi kompleksni broj koji odgovara predanom stringu.
	 * 
	 * @throws NumberFormatException ako se string ne može parsirati u kompleksni broj.
	 */
	public static ComplexNumber parse(String s) {
		
		if (s.contains("i")) {
			if (s.equals("i") || s.equals("+i"))
				return ComplexNumber.fromImaginary(1);
			if (s.equals("-i"))
				return ComplexNumber.fromImaginary(-1);
			
			StringBuilder sb = new StringBuilder();
			int index = 0;
			if (s.charAt(index) == '+' || s.charAt(index) == '-') {
				sb.append(s.charAt(index++));
			}
			while (Character.isDigit(s.charAt(index))) {
				sb.append(s.charAt(index++));
			}
			if (s.charAt(index) == '.') {
				sb.append(s.charAt(index++));
				while (Character.isDigit(s.charAt(index))) {
					sb.append(s.charAt(index++));
				}
			}
			
			//dosli do "i", mora biti kraj stringa
			if (s.charAt(index) == 'i') {
				if (index == s.length()-1)
					return ComplexNumber.fromImaginary(Double.parseDouble(sb.toString()));
				throw new NumberFormatException("Given string " + s + " is not a valid complex number!");
			}
			
			//dosli do + ili -
			double real = Double.parseDouble(sb.toString());
			sb.delete(0, index);
			if (s.charAt(index) == '+' || s.charAt(index) == '-') {
				sb.append(s.charAt(index++));
				while (Character.isDigit(s.charAt(index))) {
					sb.append(s.charAt(index++));
				}
				if (s.charAt(index) == '.') {
					sb.append(s.charAt(index++));
					while (Character.isDigit(s.charAt(index))) {
						sb.append(s.charAt(index++));
					}
				}
				if (s.charAt(index) == 'i' && index == s.length()-1)
					return new ComplexNumber(real, Double.parseDouble(sb.length() == 1 ? sb.append("1").toString() : sb.toString()));
				throw new NumberFormatException("Given string " + s + "is not a valid complex number!");
			}
			throw new NumberFormatException("Given string " + s + " is not a valid complex number!");
			
		}
		try {
			return ComplexNumber.fromReal(Double.parseDouble(s));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Given string " + s + " is not a valid complex number!");
		}
	}
	
	/**
	 * Metoda vraća zapis kompleksnog broja u Kartezijevim koordinatama.
	 * 
	 * @return String koji predstavlja kompleksni broj.
	 */
	public String toString() {
		if (real == 0 && imaginary != 0) {
			if (imaginary == 1)
				return "i";
			if (imaginary == -1)
				return "-i";
			return Double.toString(imaginary) + "i";
		}
		if (real != 0 && imaginary == 0) {
			return Double.toString(real);
		}
		if (real != 0 && imaginary != 0) {
			if (imaginary > 0)
				return Double.toString(real) + "+" + Double.toString(imaginary) + "i";
			return Double.toString(real) + Double.toString(imaginary) + "i";
		}
		return "0";
	}
	
	/**
	 * Metoda vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio kompleksnog broja.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Metoda vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Metoda vraća modul kompleksnog broja.
	 * 
	 * @return modul kompleksnog broja.
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Metoda vraća kut kompleksnog broja.
	 * 
	 * @return kut kompleksnog broja.
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		return (angle < 0) ? angle+2*Math.PI : angle;
	}
	
	/**
	 * Metoda za zbrajanje dvaju kompleksnih brojeva.
	 * 
	 * @param c kompleksni broj koji se želi pribrojiti trenutnom.
	 * 
	 * @return zbroj kompleksnih brojeva.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Metoda za oduzimanje dvaju kompleksnih brojeva.
	 * 
	 * @param c kompleksni broj koji se želi oduzeti od trenutnog.
	 * 
	 * @return razlika kompleksnih brojeva.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Metoda za množenje dvaju kompleksnih brojeva.
	 * 
	 * @param c kompleksni broj koji se želi pomnožiti s trenutnim.
	 * 
	 * @return umnožak kompleksnih brojeva.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Metoda za dijeljenje dvaju kompleksnih brojeva.
	 * 
	 * @param c kompleksni broj kojim se želi podijeliti trenutni.
	 * 
	 * @return kvocijent kompleksnih brojeva.
	 * 
	 * @throws IllegalArgumentException ako se pokuša dijeliti s nulom.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c.real == 0 && c.imaginary == 0)
			throw new IllegalArgumentException("Dijeljenje nulom nije dozvoljeno!");
		
		ComplexNumber conjugate = new ComplexNumber(c.real, -c.imaginary);
		ComplexNumber numerator = mul(conjugate);
		double denominator = c.getMagnitude() * c.getMagnitude();
		
		return new ComplexNumber(numerator.real / denominator, numerator.imaginary / denominator);
	}
	
	/**
	 * Metoda za potenciranje kompleksnih brojeva.
	 * 
	 * @param n potencija na koju želimo podići kompleksni broj.
	 * 
	 * @return tražena potencija kompleksnog broja.
	 * 
	 * @throws IllegalArgumentException ako se zada eksponent manji od nula.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) 
			throw new IllegalArgumentException("Power should be >=0, and it was " + n + ".");
		
		double newMagnitude = Math.pow(getMagnitude(), n);
		double newAngle = n * getAngle();		
		
		return ComplexNumber.fromMagnitudeAndAngle(newMagnitude, newAngle);
	}
	
	/**
	 * Metoda za korjenovanje kompleksnih brojeva.
	 * 
	 * @param n korijen koji se želi izračunati.
	 * 
	 * @return traženi korijen kompleksnog broja.
	 * 
	 * @throws IllegalArgumentException ako se preda parametar manji ili jednak nuli.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("The argument should be >0, and it was " + n + ".");
		
		double newMagnitude = Math.pow(getMagnitude(), 1.0/n);
		ComplexNumber[] result = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			result[i] = ComplexNumber.fromMagnitudeAndAngle(newMagnitude, (getAngle()+2*i*Math.PI)/n);
		}
		
		return result;
	}	

}
