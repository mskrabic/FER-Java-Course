package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

/**
 * Razred se koristi za izgradnju Lindenmayerovih sustava.
 * 
 * @author mskrabic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Pretpostavljena vrijednost jedinične dužine.
	 */
	private static final double DEFAULT_UNIT_LENGTH = 0.1;
	
	/**
	 * Pretpostavljena vrijednost faktora skaliranja jedinične dužine.
	 */
	private static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	
	/**
	 * Pretpostavljeno ishodište.
	 */
	private static final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	
	/**
	 * Pretpostavljeni kut.
	 */
	private static final double DEFAULT_ANGLE = 0;
	
	/**
	 * Pretpostavljeni aksiom.
	 */
	private static final String DEFAULT_AXIOM = "";
	
	
	/**
	 * Jedinična dužina.
	 */
	private double unitLength;
	
	/**
	 * Faktor skaliranja jedinične dužine.
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * Ishodište sustava.
	 */
	private Vector2D origin;
	
	/**
	 * Kut koji kornjača zatvara s pozitivnom x-osi.
	 */
	private double angle;
	
	/**
	 * Aksiom sustava.
	 */
	private String axiom;
	
	/**
	 * Produkcije sustava.
	 */
	private Dictionary<Character, String> productions;
	
	/**
	 * Naredbe sustava.
	 */
	private Dictionary<Character, Command> actions;
	
	/**
	 * Konstruktor koji inicijalizira sustav na predpostavljene vrijednosti.
	 */
	public LSystemBuilderImpl() {
		unitLength = DEFAULT_UNIT_LENGTH;
		unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
		origin = DEFAULT_ORIGIN;
		angle = DEFAULT_ANGLE;
		axiom = DEFAULT_AXIOM;
		productions = new Dictionary<>();
		actions = new Dictionary<>();
	}

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] text) {	
		for (String line : text) {
			if (line.isBlank()) {
				continue;
			}
			String[] splitted = line.split("\\s+");
			try {
				switch (splitted[0]) {
					case "origin":
						this.setOrigin(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]));
						break;
					case "unitLength":
						this.setUnitLength(Double.parseDouble(splitted[1]));
						break;
					case "unitLengthDegreeScaler":
						setScalerFromText(splitted);
						break;
					case "angle":
						this.setAngle(Double.parseDouble(splitted[1])*Math.PI/180);
						break;
					case "axiom":
						this.setAxiom(splitted[1]);
						break;
					case "command":
						if (splitted.length == 3) {
							this.actions.put(splitted[1].charAt(0), parseCommand(splitted[2]));
						} else {							
							this.actions.put(splitted[1].charAt(0), parseCommand(splitted[2] + " " + splitted[3]));
						}
						break;
					case "production":
						this.productions.put(splitted[1].charAt(0), splitted[2]);
						break;
					default:
						throw new IllegalArgumentException();
				}	
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid data:" + line);
			}
		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command c = parseCommand(action);
		actions.put(symbol, c);
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double originX, double originY) {
		this.origin = new Vector2D(originX, originY);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Metoda kreira odgovarajuću naredbu na temelju predanog stringa.
	 * 
	 * @param command string koji predstavlja željenu naredbu.
	 * 
	 * @return željena naredba.
	 * 
	 * @throws UnsupportedOperationException ako se zada neispravna naredba.
	 */
	private Command parseCommand(String command) {
		String[] arr = command.split(" ");
		switch (arr[0].toLowerCase()) {
			case "color":
				return new ColorCommand(Color.decode("#" + arr[1].toUpperCase()));
			case "draw":
				return new DrawCommand(Double.parseDouble(arr[1]));
			case "pop":
				return new PopCommand();
			case "push":
				return new PushCommand();
			case "rotate":
				return new RotateCommand(Double.parseDouble(arr[1]));
			case "scale":
				return new ScaleCommand(Double.parseDouble(arr[1]));
			case "skip":
				return new SkipCommand(Double.parseDouble(arr[1]));
			default:
				throw new UnsupportedOperationException("Unsupported command: " + arr[0].toLowerCase() + ".");
		}
	}
	
	/**
	 * Pomoćna metoda za parsiranje faktora skaliranja jedinične dužine. Prihvaća se jedan decimalan broj, ili format
	 * (decimalni broj)/(decimalni broj) s proizvoljno mnogo razmaka između svega.
	 * 
	 * @param splitted ulazna linija rastavljena razmacima.
	 */
	private void setScalerFromText(String[] splitted) {
		if (splitted.length == 2) {
			if (splitted[1].contains("//")) {
				String[] elements = splitted[1].split("/");
				this.setUnitLengthDegreeScaler(Double.parseDouble(elements[0]) / Double.parseDouble(elements[1]));
			} else {
				this.setUnitLengthDegreeScaler(Double.parseDouble(splitted[1]));				
			}
			return;
		}
		if (splitted.length == 3) {
			splitted[1] = splitted[1].replace("/", "");
			splitted[2] = splitted[2].replace("/", "");
			this.setUnitLengthDegreeScaler(Double.parseDouble(splitted[1]) / Double.parseDouble(splitted[2]));
			return;
		}
		this.setUnitLengthDegreeScaler(Double.parseDouble(splitted[1]) / Double.parseDouble(splitted[3]));
		return;
	}
	
	
	/**
	 * Razred modelira jedan Lindenmayerov sustav.
	 * 
	 * @author mskrabic
	 *
	 */
	private class LSystemImpl implements LSystem {
		
		/**
		 * Kolekcija za čuvanje već izračunatih razina krivulje.
		 */
		private ArrayIndexedCollection<String> axioms;
		
		/**
		 * Konstruktor koji inicijalizira kolekciju i dodaje u nju početni aksiom.
		 */
		public LSystemImpl() {
			this.axioms = new ArrayIndexedCollection<>();
			axioms.add(axiom);
		}

		@Override
		public void draw(int level, Painter painter) {
			String text = generate(level);
			Context ctx = new Context();
			TurtleState state = new TurtleState(origin.copy(), new Vector2D(1, 0).rotated(angle), Color.black, unitLength*Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(state);
			
			for (char action : text.toCharArray()) {
				Command command = actions.get(action);
				if (command != null) {
					command.execute(ctx, painter);
				}
			}
		}

		@Override
		public String generate(int level) {
			if (axioms.size() > level) {
				return axioms.get(level);
			}
			
			String result = axioms.get(axioms.size()-1);
			for (int i = axioms.size()-1; i < level; i++) {
				StringBuilder sb = new StringBuilder();
				for (char c : result.toCharArray()) {
					String replacement = productions.get(c);
					if (replacement == null) {
						sb.append(c);
					} else {
						sb.append(replacement);
					}
				}
				result = sb.toString();
				axioms.add(result);
			}
			return result;
		}
		
	}

}
