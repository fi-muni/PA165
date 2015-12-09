package cz.muni.fi.pa165.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception annotated to be converted to the INTERNAL_SERVER_ERROR HTTP status.
 */
public class ServerProblemException extends RuntimeException {

    public ServerProblemException(String message) {
        super(message);
    }
}
