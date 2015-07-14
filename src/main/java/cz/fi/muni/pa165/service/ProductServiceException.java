package cz.fi.muni.pa165.service;

public class ProductServiceException extends RuntimeException {

	public ProductServiceException() {
		super();
	}

	public ProductServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductServiceException(String message) {
		super(message);
	}

	public ProductServiceException(Throwable cause) {
		super(cause);
	}

}
