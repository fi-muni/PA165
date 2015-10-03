package cz.fi.muni.pa165.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.fi.muni.pa165.entity.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.User;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OrderDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	public ProductDao productDao;

	@Autowired
	public OrderDao orderDao;

	@Autowired
	public UserDao userDao;

	private Order o1;
	private Order o2;
	private Order o3;
	private Date date1;
	private Date date2;
	private Date date3;
	private User user1;
	private User user2;
	
	@BeforeMethod
	public void createOrders(){
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 1, 1);
		date1 = cal.getTime();
		cal.set(2015, 4, 5);
		date2 = cal.getTime();
		cal.set(2015, 6, 6);
		date3 = cal.getTime();

		user1 = UserDaoTest.getSimpleUser();
		user2 = UserDaoTest.getSimpleUser2();
		userDao.create(user1);
		userDao.create(user2);
		
		o1 = new Order();
		o1.setCreated(date1);
		o1.setState(OrderState.CANCELED);
		o1.setUser(user1);
		
		o2 = new Order();
		o2.setCreated(date2);
		o2.setState(OrderState.RECEIVED);
		o2.setUser(user1);
		
		o3 = new Order();
		o3.setCreated(date3);
		o3.setState(OrderState.CANCELED);
		o3.setUser(user2);
		
		orderDao.create(o1);
		orderDao.create(o2);
		orderDao.create(o3);		
	}
	
	@Test
	public void nonExistentReturnsNull() {
		Assert.assertNull(orderDao.findById(321321l));
	}

	
	@Test
	public void find() {
		Order found = orderDao.findById(o1.getId());

		Assert.assertEquals(found.getCreated(), date1);
		Assert.assertEquals(found.getState(), OrderState.CANCELED);
		Assert.assertEquals(found.getUser().getEmail(), "filip@seznam.cz");

	}

	@Test
	public void findByUser() {
		List<Order> orderes = orderDao.findByUser(user1);
		Assert.assertEquals(orderes.size(), 2);
	}

	@Test
	public void getOrdersWithState() {
		Assert.assertEquals(orderDao.getOrdersWithState(OrderState.SHIPPED).size(),0);
		
		List<Order> canceled = orderDao.getOrdersWithState(OrderState.CANCELED);
		List<Order> received = orderDao.getOrdersWithState(OrderState.RECEIVED);
		
		Assert.assertEquals(canceled.size(), 2);
		Assert.assertEquals(received.size(), 1);
		
	}

	@Test
	public void getOrdersCreatedBetween() {
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 1, 1);
		Date date1 = cal.getTime();
		cal.set(2017, 4, 5);
		Date date2 = cal.getTime();
		
		Assert.assertEquals(orderDao.getOrdersCreatedBetween(date1, date2, OrderState.RECEIVED).size(),0);
		
		cal.set(2015, 1, 1,0,0,0);
		Date date3 = cal.getTime();
		cal.set(2015, 5, 5,0,0,0);
		Date date4 = cal.getTime();
		Assert.assertEquals(orderDao.getOrdersCreatedBetween(date3, date4, OrderState.CANCELED).size(),1);
	}

}
