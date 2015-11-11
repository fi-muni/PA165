package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class OrderServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private OrderDao orderDao;

    @Mock
    private TimeService timeService;

    @Mock
    private ExchangeService exchangeService;

    @Autowired
    @InjectMocks
    private OrderService orderService;

    private Order orderReceived;
    private Order orderShipped;

    @BeforeMethod
    public void createOrders() {
        orderReceived = new Order();
        orderShipped = new Order();

        orderReceived.setState(OrderState.RECEIVED);
        orderShipped.setState(OrderState.SHIPPED);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllOrdersLastWeekTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.FEBRUARY, 10, 0, 0, 0);
        Date fabricatedTime = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date weekBeforeFabricatedTime = cal.getTime();

        Order o = new Order(4l);
        o.setState(OrderState.CANCELED);
        o.setCreated(new Date());

        when(orderDao.getOrdersCreatedBetween(any(Date.class), any(Date.class), any())).thenReturn(Collections.singletonList(o));
        when(timeService.getCurrentTime()).thenReturn(fabricatedTime);

        List<Order> orders = orderService.getAllOrdersLastWeek(OrderState.CANCELED);
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(4l));

        verify(orderDao).getOrdersCreatedBetween(weekBeforeFabricatedTime, fabricatedTime, OrderState.CANCELED);
    }


    @Test
    public void ship() {
        orderService.shipOrder(orderReceived);
        Assert.assertEquals(orderReceived.getState(), OrderState.SHIPPED);
    }

    @Test
    public void finish() {
        orderService.finishOrder(orderShipped);
        Assert.assertEquals(orderShipped.getState(), OrderState.DONE);
    }

    @Test
    public void cancel() {
        orderService.cancelOrder(orderReceived);
        Assert.assertEquals(orderReceived.getState(), OrderState.CANCELED);
    }

    @Test
    public void testGetTotalPrice() {
        Order order = new Order();
        order.setId(1l);
        order.addOrderItem(orderitem(2, 3, Currency.CZK));
        order.addOrderItem(orderitem(5, 7, Currency.EUR));
        when(orderDao.findById(order.getId())).thenReturn(order);
        when(exchangeService.getCurrencyRate(Currency.EUR, Currency.CZK)).thenReturn(new BigDecimal(27));
        Price totalPrice = orderService.getTotalPrice(1l, Currency.CZK);
        Price expected = new Price();
        expected.setCurrency(Currency.CZK);
        expected.setValue(new BigDecimal(2 * 3 + 5 * 7 * 27));
        Assert.assertEquals(totalPrice, expected, "order total price is wrong");
    }

    private static OrderItem orderitem(int amount, int price, Currency currency) {
        OrderItem item = new OrderItem();
        item.setAmount(amount);
        Price p = new Price();
        p.setValue(new BigDecimal(price));
        p.setCurrency(currency);
        item.setPricePerItem(p);
        return item;
    }

}
