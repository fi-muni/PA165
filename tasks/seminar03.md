## Seminar 03 Tasks
**Task 01** Checkout branch seminar03 from https://github.com/fi-muni/PA165 and
open project currency-convertor. 

**Task 02** Add spring-context as a maven dependency into `pom.xml`:
```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.0.6.RELEASE</version>
  </dependency> 
```

**Task 03** Create class ExchangeRateTableImpl in package cz.muni.fi.pa165.currency.
This class will implement ExchangeRateTable interface, method getExchangeRate
will return fixed exchange rate 27 for conversion from EUR to CZK and null for
any other currencies.

**Task 04** Create Spring xml application context which will configure
components ExchangeRateTableImpl and CurrencyConvertorImpl. Create MainXml class
in package cz.muni.fi.pa165 with main method which will create Spring
ApplicationContext based on this Spring xml application context configuration.
Then get instance of CurrencyConvertor and try convert one euro to czk. Test, if
the main method is working well.

**Task 05** Add inject-api as a maven dependency into `pom.xml`:
```xml
        <dependency>
            <groupId>javax.inject</groupId>
            <artifactId>javax.inject</artifactId>
            <version>1</version>
        </dependency>
```

**Task 06** Add JSR-330 annotations to ExchangeRateTableImpl and
CurrencyConvertorImpl components. Create MainAnnotations class
in package cz.muni.fi.pa165 with main method which will create Spring
ApplicationContext based on these annotations.
Then get instance of CurrencyConvertor and try convert one euro to czk. Test, if
the main method is working well.

**Task 07** Create JavaConfig Spring application context which will configure
components ExchangeRateTableImpl and CurrencyConvertorImpl. Create MainJavaConfig
class in package cz.muni.fi.pa165 with main method which will create Spring
ApplicationContext based on this JavaConfig application context configuration.
Then get instance of CurrencyConvertor and try convert one euro to czk. Test, if
the main method is working well.

Warning: You may unknowingly create two instances of CurrencyConvertor if you
both have the method annotated with @Bean and enabled scanning of components which finds the class annotated with @Named. In that case remove the scanning of
components, or specify the bean name as the first parameter of the getBean() method.

**Task 08** Add spring-aop, aspectjrt and aspectjweaver as a maven dependency
into `pom.xml`:
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>4.0.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.7.3</version>
        </dependency>
```

**Task 09** Create and configure aspect, which will print duration of each
public method call. Use example from lecture for inspiration (see LoggingAspect 
and SpringJavaConfig classes). Don't forget to enable automatic aspectj proxy
creation (with @EnableAspectJAutoProxy annotation in JavaConfig class.
