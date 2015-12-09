package cz.muni.fi.pa165.restapi.exceptions;

/**
 * Represents JSON message with error.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class ErrorResource {

    private String code;
    private String message;

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResource{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
