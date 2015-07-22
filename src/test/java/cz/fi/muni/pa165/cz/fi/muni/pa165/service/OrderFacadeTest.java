package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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




	//TODO implement this test
	@Test
	public void getAllOrdersLastWeekTest() {
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