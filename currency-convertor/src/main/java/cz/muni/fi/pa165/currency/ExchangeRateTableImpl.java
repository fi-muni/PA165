package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == null || targetCurrency == null) {
            throw new IllegalArgumentException("Params cannot be null!");
        }

        if (sourceCurrency.getCurrencyCode() == "EUR" && targetCurrency.getCurrencyCode() == "CZK") {
            return new BigDecimal("27");
        }

        return  null;
    }
}
