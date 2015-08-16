package cz.fi.muni.pa165.cz.fi.muni.pa165.facade;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.OrderItemDao;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.facade.OrderFacade;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.OrderServiceException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class OrderFacadeTest extends AbstractTestNGSpringContextTests {
	@Mock
	private OrderDao orderDao;

	@Mock
	private OrderItemDao orderItemDao;


	@Autowired	
	@InjectMocks
	private OrderService orderService;

	
	@Autowired	
	private OrderFacade orderFacade;

	@BeforeClass
	public void setup() throws ServiceException {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getAllOrdersLastWeekTest() {
		Order o = new Order(4l);
		o.setState(OrderState.CANCELED);
		o.setCreated(new Date());
		when(orderDao.getOrdersCreatedBetween(any(Date.class), any(Date.class), eq(OrderState.CANCELED))).thenReturn(Collections.singletonList(o));
		Assert.assertEquals(1, orderFacade.getAllOrdersLastWeek(OrderState.CANCELED).size());
		Assert.assertTrue(orderFacade.getAllOrdersLastWeek(OrderState.CANCELED).get(0).getId().equals(4l));
	}
	
	@Test
	public void shipOrder() {
		Order o = new Order(4l);
		o.setState(OrderState.RECEIVED);
		doReturn(o).when(orderDao).findById(4l);
		orderFacade.shipOrder(4l);
		Assert.assertEquals(o.getState(), OrderState.SHIPPED);
	}

	
	@Test(expectedExceptions = OrderServiceException.class)
	public void stateChangeNotAllowed() {
		Order o = new Order(4l);
		o.setState(OrderState.SHIPPED);
		doReturn(o).when(orderDao).findById(4l);
		orderFacade.cancelOrder(4l);
	}
}