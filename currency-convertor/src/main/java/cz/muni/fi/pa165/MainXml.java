package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.SpringJavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static  void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-config.xml");

        CurrencyConvertor currencyConvertor = appContext.getBean(CurrencyConvertor.class);
        BigDecimal result = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal("1"));

        System.out.println(result);
    }
}
