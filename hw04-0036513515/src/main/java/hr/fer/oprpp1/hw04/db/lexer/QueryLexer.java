package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Razred predstavlja jednostavni leksički analizator.
 * 
 * @author mskrabic
 * 
 */
public class QueryLexer {

	/**
	 * Ulazni tekst.
	 */
	private char[] data;

	/**
	 * Trenutni token.
	 */
	private QueryToken token;

	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex;

	/**
	 * Konstruktor koji prima ulazni tekst.
	 * 
	 * @param text ulazni tekst koji se želi tokenizirati.
	 * 
	 * @throws NullPointerException ako se preda <code>null</code> kao ulazni tekst.
	 */
	public QueryLexer(String query) {
		if (query == null)
			throw new NullPointerException("Input must not be null!");
		
		this.data = query.toCharArray();
		this.currentIndex = 0;
		this.token = null;
	}

	/**
	 * Metoda generira i vraća sljedeći token.
	 * 
	 * @return sljedeći token.
	 */
	public QueryToken nextToken() {
		if (this.token != null && this.token.getType() == QueryTokenType.EOF)
			throw new QueryLexerException("Already generated EOF token!");

		skipBlankSpaces();
		
		if (currentIndex == data.length) {
			this.token = new QueryToken(QueryTokenType.EOF, null);
			return this.token;
		}
		
		if (Character.isLetter(data[currentIndex])) {
			QueryToken t = isAnd();
			if (t != null) {
				this.token = t;
				return this.token;
			}
			
			t = isLike();
			if (t != null) {
				this.token = t;
				return this.token;
			}
			
			StringBuilder sb = new StringBuilder();
			while (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
			this.token = new QueryToken(QueryTokenType.VARIABLE, sb.toString());
			return this.token;
		}
		
		if (data[currentIndex] == '\"') {
			currentIndex++;
			StringBuilder sb = new StringBuilder();
			
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				sb.append(data[currentIndex++]);
			}
			if (currentIndex >= data.length)
				throw new QueryLexerException("Missing closing quote!");
			currentIndex++;
			this.token = new QueryToken(QueryTokenType.STRING, sb.toString());
			return this.token;		
		}
		
		if (data[currentIndex] == '!' && data[currentIndex+1] == '=') {
			currentIndex += 2;
			this.token = new QueryToken(QueryTokenType.OPERATOR, "!=");
			return this.token;
		}
		
		if (data[currentIndex] == '=') {
			currentIndex++;
			this.token = new QueryToken(QueryTokenType.OPERATOR, "=");
			return this.token;
		}
		
		if (data[currentIndex] == '>' || data[currentIndex] == '<') {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex++]);
			if (data[currentIndex] == '=') {
				sb.append(data[currentIndex++]);
			}
			this.token = new QueryToken(QueryTokenType.OPERATOR, sb.toString());
			return this.token;
		}
		
		throw new QueryLexerException("Invalid query element!");
	}

	/**
	 * Vraća zadnji generirani token. Ne uzrokuje generiranje novog tokena.
	 * 
	 * @return zadnji generirani token.
	 */
	public QueryToken getToken() {
		return token;
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
	 * Metoda provjerava je li sljedeći token AND.
	 * 
	 * @return AND token ako je prepoznat u ulaznom nizu, <code>null</code> inače.
	 */
	private QueryToken isAnd() {
		if (currentIndex + 2 < data.length) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]).append(data[currentIndex+1]).append(data[currentIndex+2]);
			
			String value = sb.toString().toLowerCase();
			
			if (value.equals("and")) {
				currentIndex += 3;
				return new QueryToken(QueryTokenType.AND, value);
			}	
		}
		return null;
	}
	
	/**
	 * Metoda provjerava je li sljedeći token operator LIKE.
	 * 
	 * @return novi OPERATOR token vrijednosti LIKE ako je prepoznat u ulaznom nizu, <code>null</code> inače. 
	 */
	private QueryToken isLike() {
		if (currentIndex + 3 < data.length) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]).append(data[currentIndex+1]).append(data[currentIndex+2]).append(data[currentIndex+3]);
			
			String value = sb.toString();
			
			if (value.equals("LIKE")) {
				currentIndex += 4;
				return new QueryToken(QueryTokenType.OPERATOR, value);
			}	
		}
		return null;
	}
}
