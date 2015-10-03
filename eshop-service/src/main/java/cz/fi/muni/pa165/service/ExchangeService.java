package cz.fi.muni.pa165.service;

import java.math.BigDecimal;

import cz.fi.muni.pa165.enums.Currency;

public interface ExchangeService {

	public BigDecimal getCurrencyRate(Currency currencyFrom, Currency currencyTo);
}
