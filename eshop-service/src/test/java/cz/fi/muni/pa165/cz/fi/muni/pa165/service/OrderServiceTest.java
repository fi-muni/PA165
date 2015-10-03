package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.TimeService;
import cz.fi.muni.pa165.service.config.ServiceTestConfiguration;

@ContextConfiguration(classes = ServiceTestConfiguration.class)
public class OrderServiceTest extends AbstractTestNGSpringContextTests
{
    @Mock
    private OrderDao orderDao;
    
    @Mock
    private TimeService timeService;
    
    @Autowired
    @InjectMocks
    private OrderService orderService;

    private Order orderReceived;
    private Order orderShipped;
    
    @BeforeMethod
    public void createOrders(){
    	orderReceived = new Order();
    	orderShipped = new Order();
    	
    	orderReceived.setState(OrderState.RECEIVED);
    	orderShipped.setState(OrderState.SHIPPED);
    }
    
    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllOrdersLastWeekTest() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(2015,1,10,0,0,0);
    	Date fabricatedTime = cal.getTime();
    	cal.add(Calendar.DAY_OF_MONTH, -7);
    	Date weekBeforeFabricatedTime=cal.getTime();
    	
        Order o = new Order(4l);
        o.setState(OrderState.CANCELED);
        o.setCreated(new Date());
        
        when(orderDao.getOrdersCreatedBetween(any(Date.class), any(Date.class), any())).thenReturn(Collections.singletonList(o));
        when(timeService.getCurrentTime()).thenReturn(fabricatedTime);
        
        List<Order> orders = orderService.getAllOrdersLastWeek(OrderState.CANCELED);
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(4l));
        
        verify(orderDao).getOrdersCreatedBetween(weekBeforeFabricatedTime,fabricatedTime, OrderState.CANCELED);
    }
    
    
    
    @Test
    public void ship(){
    	orderService.shipOrder(orderReceived);
    	Assert.assertEquals(orderReceived.getState(), OrderState.SHIPPED);
    }
    
    @Test
    public void finish(){
    	orderService.finishOrder(orderShipped);
    	Assert.assertEquals(orderShipped.getState(), OrderState.DONE);
    }
    
    @Test
    public void cancel(){
    	orderService.cancelOrder(orderReceived);
    	Assert.assertEquals(orderReceived.getState(), OrderState.CANCELED);
    }

}
