package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);
        //logger.info("convert?");

        if (sourceCurrency == null || targetCurrency == null || sourceAmount == null) {
            throw new IllegalArgumentException();
        }

        BigDecimal rate;

        try {
            rate = this.exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (ExternalServiceFailureException e) {
            throw new UnknownExchangeRateException("Exchange rate not available");
        }

        if (rate == null) {
            throw new UnknownExchangeRateException("Exchange rate not known");
        }

        return rate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
    };

}
