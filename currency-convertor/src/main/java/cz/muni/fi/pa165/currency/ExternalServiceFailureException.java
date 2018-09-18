package cz.muni.fi.pa165.currency;

/**
 * This exception is thrown when external service call failed.
 *
 * @author petr.adamek@embedit.cz
 */
public class ExternalServiceFailureException extends Exception {

    /**
     * Constructs an instance of <code>ExternalServiceFailureException</code>
     * with the specified detail message.
     *
     * @param message the detail message.
     */
    public ExternalServiceFailureException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>ExternalServiceFailureException</code>
     * with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause cause
     */
    public ExternalServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
