package cz.fi.muni.pa165.exceptions;

public class EshopServiceException extends RuntimeException{

	public EshopServiceException() {
		super();
	}

	public EshopServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EshopServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EshopServiceException(String message) {
		super(message);
	}

	public EshopServiceException(Throwable cause) {
		super(cause);
	}

}
