package cz.fi.muni.pa165.dto;

import java.math.BigDecimal;
import java.util.Date;

import cz.fi.muni.pa165.enums.Currency;

public class PriceDTO {
	private Long id;
	private BigDecimal value;
	private Currency currency;
	private Date priceStart;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
    public Date getPriceStart() {
        return priceStart;
    }
    public void setPriceStart(Date priceStart) {
        this.priceStart = priceStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceDTO priceDTO = (PriceDTO) o;

        if (value != null ? !value.equals(priceDTO.value) : priceDTO.value != null) return false;
        if (currency != priceDTO.currency) return false;
        return !(priceStart != null ? !priceStart.equals(priceDTO.priceStart) : priceDTO.priceStart != null);

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (priceStart != null ? priceStart.hashCode() : 0);
        return result;
    }
}
