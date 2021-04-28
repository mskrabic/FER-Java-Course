package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred predstavlja jednostavni leksički analizator.
 * 
 * @author mskrabic
 * 
 */
public class Lexer {

	/**
	 * Ulazni tekst.
	 */
	private char[] data;

	/**
	 * Trenutni token.
	 */
	private Token token;

	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex;

	/**
	 * Trenutno stanje lexera.
	 */
	private LexerState state;

	/**
	 * Konstruktor koji prima ulazni tekst.
	 * 
	 * @param text ulazni tekst koji se želi tokenizirati.
	 * 
	 * @throws NullPointerException ako se preda <code>null</code> kao ulazni tekst.
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException("Input must not be null!");
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}

	/**
	 * Metoda generira i vraća sljedeći token.
	 * 
	 * @return sljedeći token.
	 */
	public Token nextToken() {
		if (this.token != null && this.token.getType() == TokenType.EOF)
			throw new LexerException("Already generated EOF token!");

		skipBlankSpaces();

		if (this.state == LexerState.BASIC) {
			lexerBasic();
		} else {
			lexerExtended();
		}

		return this.token;
	}

	/**
	 * Vraća zadnji generirani token. Ne uzrokuje generiranje novog tokena.
	 * 
	 * @return zadnji generirani token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Metoda postavlja stanje lexera.
	 * 
	 * @param state stanje koje se želi postaviti.
	 * 
	 * @throws NullPointerException ako se preda <code>null</code>.
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("State can't be null!");
		
		this.state = state;
	}
	
									/* **********************************************
									 * Pomoćne metode koje doprinose čitljivosti koda.
									 * **********************************************/

	/**
	 * Pomoćna metoda koja provjerava je li sljedeći nepročitani znak praznina.
	 * 
	 * @return <code>true</code> ako je sljedeći nepročitani znak praznina,
	 *         <code>false</code> inače.
	 */
	private boolean isSpace() {
		char c = data[currentIndex];
		return (c == ' ' || c == '\r' || c == '\n' || c == '\t');
	}

	/**
	 * Pomoćna metoda koja preskače sve uzastopne praznine.
	 */
	private void skipBlankSpaces() {
		while (true) {
			if (currentIndex < data.length && isSpace()) {
				currentIndex++;
			} else {
				break;
			}
		}
	}

	/**
	 * Pomoćna metoda za rad u stanju BASIC.
	 */
	private void lexerBasic() {
		StringBuilder sb = new StringBuilder();
		TokenType type = TokenType.EOF;

		while (true) {
			if (currentIndex == data.length)
				break;
			if (Character.isLetter(data[currentIndex])) {
				if (type == TokenType.EOF) {
					type = TokenType.WORD;
				} else if (type != TokenType.WORD) {
					break;
				}
				sb.append(data[currentIndex++]);
			} else if (data[currentIndex] == '\\') {
				if (type == TokenType.EOF) {
					type = TokenType.WORD;
				} else if (type != TokenType.WORD) {
					break;
				}
				currentIndex++;
				if (currentIndex == data.length
						|| !(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\'))
					throw new LexerException("Invalid escape!");
				sb.append(data[currentIndex++]);
			} else if (Character.isDigit(data[currentIndex])) {
				if (type == TokenType.EOF) {
					type = TokenType.NUMBER;
				} else if (type != TokenType.NUMBER) {
					break;
				}
				sb.append(data[currentIndex++]);
			} else if (!isSpace()) {
				if (type == TokenType.EOF) {
					type = TokenType.SYMBOL;
					sb.append(data[currentIndex++]);
				}
				break;
			} else {
				break;
			}
		}
		createToken(type, sb.toString());
	}

	/**
	 * Pomoćna metoda za rad u stanju EXTENDED.
	 */
	private void lexerExtended() {
		StringBuilder sb = new StringBuilder();
		TokenType type = TokenType.EOF;

		while (true) {
			if (currentIndex == data.length) {
				break;
			}
			if (data[currentIndex] == '#') {
				if (type == TokenType.EOF) {
					type = TokenType.SYMBOL;
					sb.append(data[currentIndex++]);
					break;
				} else {
					break;
				}
			} else if (!isSpace()) {
				type = TokenType.WORD;
				sb.append(data[currentIndex++]);
			} else {
				break;
			}
		}
		createToken(type, sb.toString());
	}

	/**
	 * Pomoćna metoda za postavljanje tokena.
	 * 
	 * @param type tip tokena.
	 * @param s vrijednost tokena.
	 */
	private void createToken(TokenType type, String s) {
		switch (type) {
			case EOF:
				this.token = new Token(type, null);
				break;
			case NUMBER:
				try {
					Long l = Long.parseLong(s);
					this.token = new Token(type, l);
				} catch (NumberFormatException e) {
					throw new LexerException("Invalid input: Number cannot be parsed into a Long");
				}
				break;
			case WORD:
				this.token = new Token(type, s);
				break;
			case SYMBOL:
				this.token = new Token(type, s.charAt(0));
				break;
		}
	}

}
