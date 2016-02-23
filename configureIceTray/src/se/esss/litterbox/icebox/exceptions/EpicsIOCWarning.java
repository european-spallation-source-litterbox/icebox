package se.esss.litterbox.icebox.exceptions;

public class EpicsIOCWarning extends Exception {

	private static final long serialVersionUID = 8962361111465379175L;

	public EpicsIOCWarning() {

	}

	public EpicsIOCWarning(String message) {
		super(message);
	}

	public EpicsIOCWarning(Throwable cause) {
		super(cause);
	}

	public EpicsIOCWarning(String message, Throwable cause) {
		super(message, cause);
	}

	public EpicsIOCWarning(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
