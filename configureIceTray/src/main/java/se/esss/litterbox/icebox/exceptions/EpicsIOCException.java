package se.esss.litterbox.icebox.exceptions;

public class EpicsIOCException extends Exception {

	private static final long serialVersionUID = 8962361111465379175L;

	public EpicsIOCException() {

	}

	public EpicsIOCException(String message) {
		super(message);
	}

	public EpicsIOCException(Throwable cause) {
		super(cause);
	}

	public EpicsIOCException(String message, Throwable cause) {
		super(message, cause);
	}

	public EpicsIOCException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
