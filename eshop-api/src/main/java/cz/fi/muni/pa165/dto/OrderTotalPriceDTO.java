package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Currency;

import java.math.BigDecimal;

/**
 * Provides total price of an order. Computing total price must be done on service layer,
 * as it involves converting currencies.
 *
 */
public class OrderTotalPriceDTO {

    private OrderDTO order;

    private BigDecimal price;

    private Currency currency;

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTotalPriceDTO that = (OrderTotalPriceDTO) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return currency == that.currency;

    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderTotalPriceDTO{" +
                "order=" + order +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
