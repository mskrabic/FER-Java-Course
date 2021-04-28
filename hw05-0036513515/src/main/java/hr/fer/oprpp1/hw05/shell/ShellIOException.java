package hr.fer.oprpp1.hw05.shell;

/**
 * Iznimka koju u slučaju pogreške pri čitanju/pisanju linije baca {@link MyShell}.
 * 
 * @author mskrabic
 *
 */
public class ShellIOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ShellIOException() {
		super();
	}
	
	public ShellIOException(String message) {
		super(message);
	}

}
