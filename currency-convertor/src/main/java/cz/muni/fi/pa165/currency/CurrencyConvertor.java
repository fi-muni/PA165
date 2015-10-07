package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * CurrencyConvertor performs conversions between currencies.
 *
 * @author petr.adamek@embedit.cz
 */
public interface CurrencyConvertor {

    /**
     * Returns amount of money in target currency corresponding to given amount
     * of money in source currency.
     * 
     * <p>Result is always rounded to have two digits after the decimal point
     * with {@link RoundingMode#HALF_EVEN}.
     *
     * @param sourceCurrency the source currency
     * @param targetCurrency the target currency
     * @param sourceAmount amount of money in source currency
     * @return amount of money in target currency corresponding to given amount
     * of money in source currency.
     * @throws IllegalArgumentException when sourceCurrency, targetCurrency or
     * sourceAmount is null
     * @throws UnknownExchangeRateException when the exchange rate is not known,
     * because the lookup failed or information about given currencies pair is
     * not available
     *
     */
    BigDecimal convert(Currency sourceCurrency, Currency targetCurrency,
            BigDecimal sourceAmount);

}
