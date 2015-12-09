package cz.muni.fi.pa165.restapi.exceptions;

/**
 * Exception annotated to be converted to the NOT_FOUND HTTP status.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
