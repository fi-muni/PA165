package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * ExchangeRateTable provides exchange rates. Source of information depends on
 * specific implementation.
 *
 * @author petr.adamek@embedit.cz
 */
public interface ExchangeRateTable {

    /**
     * Returns exchange rate for given currencies pair. Exchange rate is amount
     * of units in target currency which corresponds to one unit in source
     * currency.
     *
     * <p>If source currency is EUR and target currency is CZK and current
     * exchange rate is 25 CZK for 1 EUR, 25 is returned<p>
     *
     * @param sourceCurrency source currency
     * @param targetCurrency target currency
     * @return exchange rate for given currencies pair or null if exchange rate
     * for given pair is not known.
     * @throws IllegalArgumentException when sourceCurrency or targetCurrency is
     * null
     * @throws ExternalServiceFailureException when lookup for current exchange
     * rate failed.
     */
    BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency)
            throws ExternalServiceFailureException;

}
