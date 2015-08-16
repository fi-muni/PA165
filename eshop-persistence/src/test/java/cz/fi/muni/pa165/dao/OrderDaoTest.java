package cz.fi.muni.pa165.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.fi.muni.pa165.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.User;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderDaoTest  extends AbstractTransactionalTestNGSpringContextTests{
	@PersistenceContext
	public EntityManager em;
	
	@Autowired
	public ProductDao productDao;

	@Autowired
	public OrderDao orderDao;
	
	@Autowired
	public UserDao userDao;
	
	@Test
	public void simpleTest(){
		Product p  = new Product();
		p.setName("LCD TV");
		productDao.create(p);
		
		User user =UserDaoTest.getSimpleUser();
		userDao.create(user);
		OrderItem item = new OrderItem();
		item.setProduct(p);
		Order order = new Order();
		order.addOrderItem(item);
		order.setUser(user);
		order.setCreated(Calendar.getInstance().getTime());
		order.setState(OrderState.RECEIVED);
		orderDao.create(order);
		List<Order> orders = orderDao.findAll();
		Assert.assertEquals(orders.get(0).getState(), OrderState.RECEIVED);
		Assert.assertTrue(orders.get(0).getOrderItems().get(0).getProduct().getName().equals("LCD TV"));
		
	}
}
