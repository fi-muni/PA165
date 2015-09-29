## Seminar 02 Tasks
**Task 01** Checkout branch seminar02 from https://github.com/fi-muni/PA165 and
open project currency-convertor. Look at the interfaces ExchangeRateTable and
CurrencyConvertor in the package cz.fi.muni.pa165.currency and read their contract.

**Task 02** Add mockito-all as a maven dependency into `pom.xml`:
```xml
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-all</artifactId>
        <version>1.10.19</version>
        <scope>test</scope>
  </dependency> 
```

**Task 03** Implement `testConvert()` method in `CurrencyConvertorImplTest`.
Use Mockito for creating mocks and don't forget to test border values and proper
rounding. Ask your teacher to check if the test is well written.
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
        <version>1.7.12</version>
    </dependency>
```
and modify `CurrencyConvertorImpl` class to log:
* Each call of convert() method as a trace
* Each conversion failure due missing exchange rate for given currencies as a warning
* Each conversion failure due `ExternalServiceFailureException` as an error

Don't forget to log all useful context information, but avoid unnecessary string
concatenations.

**Task 07** Add log4j and slf4j-log4j12 as the maven dependency:
```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
	<version>1.7.12</version>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
```
Change log4j configuration to log also trace messages (see log4j.properties file)
and try to start tests to check if logging works.