package cz.muni.fi.pa165.restapi.exceptions;

/**
 * Invalid request exception.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
