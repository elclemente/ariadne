package de.jmens.ariadne.exception;

public class CannotLoadFileException extends Exception {

	public CannotLoadFileException() {
		super();
	}

	public CannotLoadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CannotLoadFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotLoadFileException(String message) {
		super(message);
	}

	public CannotLoadFileException(Throwable cause) {
		super(cause);
	}

}
