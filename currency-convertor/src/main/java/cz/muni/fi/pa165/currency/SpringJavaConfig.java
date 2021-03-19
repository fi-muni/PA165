package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.inject.Inject;

@Configuration
@ComponentScan("cz.muni.fi.pa165")
@EnableAspectJAutoProxy
public class SpringJavaConfig {

    @Inject
    private ExchangeRateTable exchangeRateTable;

    @Bean
    public CurrencyConvertor currencyConvertor() {
        return new CurrencyConvertorImpl(exchangeRateTable);
    }

    //@Bean
    //public ExchangeRateTable exchangeRateTable() {
    //    return new ExchangeRateTableImpl();
    //}
}
