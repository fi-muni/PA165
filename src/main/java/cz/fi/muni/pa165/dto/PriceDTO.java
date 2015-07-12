package cz.fi.muni.pa165.dto;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mbriskar on 7/12/15.
 */
public class PriceDTO
{
    private Long id;

    private BigDecimal value;

    private Date priceStart;

    private Currency currency;

    public enum Currency{
        CZK, EUR, USD
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }


    public Date getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Date priceStart) {
        this.priceStart = priceStart;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long  getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                    + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result
                    + ((priceStart == null) ? 0 : priceStart.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PriceDTO other = (PriceDTO) obj;
        if (currency != other.currency)
            return false;
        if (priceStart == null) {
            if (other.priceStart != null)
                return false;
        } else if (!priceStart.equals(other.priceStart))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
