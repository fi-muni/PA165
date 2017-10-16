package cz.fi.muni.carshop.exceptions;

public class RequestedCarNotFoundException extends Exception {

	public RequestedCarNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestedCarNotFoundException(String message) {
		super(message);
	}

	

}
