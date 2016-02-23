package se.esss.litterbox.icebox.exceptions;

public class IceCubeWarning extends Exception {

	private static final long serialVersionUID = 8962361111465379175L;

	public IceCubeWarning() {

	}

	public IceCubeWarning(String message) {
		super(message);
	}

	public IceCubeWarning(Throwable cause) {
		super(cause);
	}

	public IceCubeWarning(String message, Throwable cause) {
		super(message, cause);
	}

	public IceCubeWarning(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
