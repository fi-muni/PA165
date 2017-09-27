package cz.fi.muni.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;

    @Before
    public void setup() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK)).thenReturn(new BigDecimal("0.1"));

        assertEquals(new BigDecimal("1.00"), currencyConvertor.convert(EUR, CZK, new BigDecimal("10.050")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("10.051")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("10.149")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("10.150")));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertor.convert(null, CZK,BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(EUR, null,BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(EUR, CZK, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(null);
        currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {

        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenThrow(UnknownExchangeRateException.class);

        currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);
    }

}
