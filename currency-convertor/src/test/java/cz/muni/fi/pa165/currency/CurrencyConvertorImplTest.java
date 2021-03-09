package cz.muni.fi.pa165.currency;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {


    @Mock
    ExchangeRateTable table;


    @Before
    public  void init() {
        MockitoAnnotations.initMocks(this);

    }

    Currency source = Currency.getInstance("EUR");
    Currency target = Currency.getInstance("CZK");



    @Test
    public void testConvert() throws ExternalServiceFailureException {

        // Don't forget to test border values and proper rounding.

        CurrencyConvertor convertor = new CurrencyConvertorImpl(table);

        // Basic 1 EUR to CZK
        // Expected 25
        when(table.getExchangeRate(source, target)).thenReturn(new BigDecimal("25"));
        BigDecimal val = new BigDecimal("1");
        BigDecimal expectedResult = new BigDecimal("25");
        BigDecimal dconv = convertor.convert(source, target, val);
        assertThat(dconv).as("Basic check").isEqualByComparingTo(expectedResult);


        // Rounding test, rounding down. Rate 0.03785, amount 7.1717
        // Expected 0.27
        when(table.getExchangeRate(source, target)).thenReturn(new BigDecimal("0.03785"));
        val = new BigDecimal("7.1717");
        expectedResult = new BigDecimal("0.27");
        dconv = convertor.convert(source, target, val);
        assertThat(dconv).as("Rounding check down").isEqualByComparingTo(expectedResult);

        // Rounding test, rounding up. Rate 0.03785, amount 6.8
        // Expected 0.26
        val = new BigDecimal("6.8");
        expectedResult = new BigDecimal("0.26");
        when(table.getExchangeRate(source, target)).thenReturn(new BigDecimal("0.03785"));
        dconv = convertor.convert(source, target, val);
        assertThat(dconv).as("Rounding check up").isEqualByComparingTo(expectedResult);

        // Rounding equidistance. Rate 2.6225, amount 2
        // Expected 5.24 (due to half_even rounding behaviour)
        val = new BigDecimal("2");
        expectedResult = new BigDecimal("5.24");
        when(table.getExchangeRate(source, target)).thenReturn(new BigDecimal("2.6225"));
        dconv = convertor.convert(source, target, val);
        assertThat(dconv).as("Rounding check equidistance").isEqualByComparingTo(expectedResult);


        // Exchange rate 0.00013, amount 25
        // Expected 0.00 (due to two decimal places rounding)
        val = new BigDecimal("25");
        expectedResult = new BigDecimal("0.00");
        when(table.getExchangeRate(source, target)).thenReturn(new BigDecimal("0.00013"));
        dconv = convertor.convert(source, target, val);
        assertThat(dconv).as("Rounding check too small number").isEqualByComparingTo(expectedResult);

    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        // Expected IllegalArgumentException()
        CurrencyConvertorImpl conv = new CurrencyConvertorImpl(table);
        Throwable thrownExc = catchThrowable(() -> {
            conv.convert(null, target, new BigDecimal("1"));
        });

        assertThat(thrownExc).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        // Expected IllegalArgumentException()
        CurrencyConvertorImpl conv = new CurrencyConvertorImpl(table);
        Throwable thrownExc = catchThrowable(() -> {
            conv.convert(source, null, new BigDecimal("1"));
        });

        assertThat(thrownExc).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        // Expected IllegalArgumentException()
        CurrencyConvertorImpl conv = new CurrencyConvertorImpl(table);
        Throwable thrownExc = catchThrowable(() -> {
            conv.convert(source, target, null);
        });

        assertThat(thrownExc).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithUnknownCurrency() {
        // Expected UnknownExchangeRateException
        CurrencyConvertor conv = new CurrencyConvertorImpl(table);
        try {
            when(table.getExchangeRate(source, target)).thenThrow(new ExternalServiceFailureException("External service fail"));
        } catch (ExternalServiceFailureException e) {
            e.printStackTrace();
        }

        Throwable thrownExc = catchThrowable(() -> {
            conv.convert(source, target, new BigDecimal("2"));
        });
        assertThat(thrownExc).isInstanceOf(UnknownExchangeRateException.class);
    }

    @Test
    public void testConvertWithExternalServiceFailure() {
        // Expected UnknownExchangeRateException
        try {
            when(table.getExchangeRate(source, target)).thenThrow(new ExternalServiceFailureException("Service failure"));
        } catch (ExternalServiceFailureException e) {
            e.printStackTrace();
        }

        CurrencyConvertorImpl conv = new CurrencyConvertorImpl(table);
        Throwable thrownExc = catchThrowable(() -> {
            conv.convert(source, target, new BigDecimal("2"));
        });
        assertThat(thrownExc).isInstanceOf(UnknownExchangeRateException.class);

    }

}
