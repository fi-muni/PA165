**Prerequisites:** Knowledge of JUnit unit testing framework and SLF4J+Logback logging frameworks

## Seminar 02 Tasks
**Task 01** Checkout branch seminar02 from https://github.com/fi-muni/PA165 and
open project currency-convertor. Look at the interfaces ExchangeRateTable and
CurrencyConvertor in the package cz.fi.muni.pa165.currency and read their contract.

**Task 02** Add mockito-core and assertj-core as a maven dependencies into `pom.xml`:
```xml
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>2.22.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.11.1</version>
            <scope>test</scope>
        </dependency>
```

**Task 03** Implement `testConvert()` method in `CurrencyConvertorImplTest`.
Use [Mockito](http://site.mockito.org/) for creating mocks, 
[AssertJ](http://joel-costigliola.github.io/assertj/)
for assertions and do not forget to test border values and proper
rounding. Ask your teacher to check if the test is well written.
(Hint: use [Mockito tutorial](http://www.vogella.com/tutorials/Mockito/article.html).)
Tip: It is better to use `new BigDecimal("15.29")` than `new BigDecimal(15.29)`
for creating BigDecimal values in the test. Do you know, why?

**Task 04** Implement all other test methods in `CurrencyConvertorImplTest`. 

**Task 05** Implement `convert(...)` method in `CurrencyConvertorImpl` class and
try to execute tests.

**Task 06** Add slf4j-api as the maven dependency
```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
```
and modify `CurrencyConvertorImpl` class to log:
* Each call of convert() method as a trace
* Each conversion failure due missing exchange rate for given currencies as a warning
* Each conversion failure due `ExternalServiceFailureException` as an error

Do not forget to log all useful context information, but avoid unnecessary string
concatenations.

**Task 07** Add Logback as the maven dependency:
```xml
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>
```
Change logback configuration to log also trace messages (see logback.xml file)
and try to start tests to check if logging works.
