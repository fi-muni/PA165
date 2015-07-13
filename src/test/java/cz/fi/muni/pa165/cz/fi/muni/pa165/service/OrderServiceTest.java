package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.midi.Receiver;

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
import cz.fi.muni.pa165.dao.PriceRepository;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.OrderServiceException;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class OrderServiceTest extends AbstractTestNGSpringContextTests {
	@Mock
	private OrderDao orderDao;

	@Mock
	private OrderItemDao orderItemDao;

	@Mock
	private PriceRepository priceDao;

	@Autowired
	@InjectMocks
	private OrderService orderService;

	@BeforeClass
	public void setup() throws ServiceException {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Tests that all orders of specific user are queried
	 * 
	 * @param userId
	 */
	@Test
	public void getOrdersByUser() {
		orderService.getOrdersByUser(2L);
		verify(orderDao).findByUser(2L);
	}

	/**
	 * Tests that Service method filters correct orders
	 */
	@Test
	public void getAllOrdersLastWeek() {
		Order o1 = new Order();
		o1.setState(OrderState.RECEIVED);
		Order o2 = new Order();
		o2.setState(OrderState.DONE);
		List<Order> lastWeekOrders = new ArrayList<Order>();
		lastWeekOrders.add(o1);
		lastWeekOrders.add(o2);

		doReturn(lastWeekOrders).when(orderDao).getOrdersCreatedBetween(
				(Date) anyObject(), (Date) anyObject());

		List<OrderDTO> ordersDTO = orderService
				.getAllOrdersLastWeek(OrderState.RECEIVED);
		Assert.assertEquals(ordersDTO.size(), 1);
		Assert.assertEquals(ordersDTO.get(0).getState(), OrderState.RECEIVED);
	}

	@Test
	public void shipOrder() {
		Order o = new Order();
		o.setState(OrderState.RECEIVED);
		doReturn(o).when(orderDao).findById(4l);
		orderService.shipOrder(4l);
		Assert.assertEquals(o.getState(), OrderState.SHIPPED);
	}

	
	@Test(expectedExceptions = OrderServiceException.class)
	public void stateChangeNotAllowed() {
		Order o = new Order();
		o.setState(OrderState.SHIPPED);
		doReturn(o).when(orderDao).findById(4l);
		orderService.cancelOrder(4l);
	}
}