package net.edu.sartuweb.core.exceptions;

/**
 * Excepción interna de la aplicación que securiza los servicios Rest de Sartu
 */
public class SartuRestException extends RuntimeException {
	private static final long serialVersionUID = 6658626324975955779L;
	
	public SartuRestException() {
		super();
	}

	public SartuRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public SartuRestException(String message) {
		super(message);
	}

	public SartuRestException(Throwable cause) {
		super(cause);
	}
}