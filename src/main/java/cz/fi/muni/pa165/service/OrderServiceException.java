package cz.fi.muni.pa165.service;

public class OrderServiceException extends RuntimeException {

	public OrderServiceException() {
		super();
	}

	public OrderServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrderServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderServiceException(String message) {
		super(message);
	}

	public OrderServiceException(Throwable cause) {
		super(cause);
	}

}
