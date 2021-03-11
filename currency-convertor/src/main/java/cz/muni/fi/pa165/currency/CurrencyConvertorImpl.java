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
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

        if (sourceCurrency == null || targetCurrency == null || sourceAmount == null) {
            logger.trace("Convert() called with illegal argument. Source: {}, target: {}, amount: {}",
                    sourceCurrency, targetCurrency, sourceAmount);
            throw new IllegalArgumentException();
        }

        BigDecimal rate;

        try {
            rate = this.exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (ExternalServiceFailureException e) {
            logger.trace("Convert() called unsuccessful. Source: {}, target: {}, amount: {}",
                    sourceCurrency, targetCurrency, sourceAmount);
            logger.error("ExternalServiceFailureException. Source: {}, target: {}, amount: {}",
                    sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode(), sourceAmount);
            throw new UnknownExchangeRateException("External service failure");
        }

        if (rate == null) {
            logger.trace("Convert() called unsuccessful. Source: {}, target: {}, amount: {}",
                    sourceCurrency, targetCurrency, sourceAmount);
            logger.warn("Missing exchange rate for source: {} and target: {}",
                    sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
            throw new UnknownExchangeRateException("Exchange rate missing");
        }

        logger.trace("Convert() called successful. Source: {}, target: {}, amount: {}, rate: {} ",
                sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode(), sourceAmount, rate);
        return rate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
    };

}
