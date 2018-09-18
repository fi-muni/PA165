package cz.muni.fi.pa165.currency;

/**
 * This exception is thrown when required exchange rate is not known, because
 * the lookup failed of information about given currencies pair is not known.
 *
 * @author petr.adamek@embedit.cz
 */
public class UnknownExchangeRateException extends RuntimeException {

    /**
     * Constructs an instance of <code>UnsupportedCurrencyException</code> with
     * the specified detail message.
     *
     * @param message the detail message.
     */
    public UnknownExchangeRateException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>UnsupportedCurrencyException</code> with
     * the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause
     */
    public UnknownExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
