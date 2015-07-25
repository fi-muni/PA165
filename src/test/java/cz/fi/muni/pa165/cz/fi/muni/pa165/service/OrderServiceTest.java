package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.OrderItemDao;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.service.OrderService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class OrderServiceTest extends AbstractTestNGSpringContextTests
{
    @Mock
    private OrderDao orderDao;


    @Autowired
    @InjectMocks
    private OrderService orderService;

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllOrdersLastWeekTest() {
        Order o = new Order(4l);
        o.setState(OrderState.CANCELED);
        o.setCreated(new Date());
        when(orderDao.getOrdersCreatedBetween(any(Date.class), any(Date.class), eq(OrderState.CANCELED))).thenReturn(Collections.singletonList(o));
        Assert.assertEquals(1, orderService.getAllOrdersLastWeek(OrderState.CANCELED).size());
        Assert.assertTrue(orderService.getAllOrdersLastWeek(OrderState.CANCELED).get(0).getId().equals(4l));
    }

}
