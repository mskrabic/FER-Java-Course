package hr.fer.oprpp1.hw05.crypto;

/**
 * Iznimka koju {@link Crypto} baca u slučaju pogreške.
 * 
 * @author mskrabic
 *
 */
public class CryptoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CryptoException(String message) {
		super(message);
	}
	
	public CryptoException() {
		super();
	}

}
