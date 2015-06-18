package cz.fi.muni.pa165;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;
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
		
		Order order = new Order();
		order.addOrderItem(p);
		order.setUser(user);
		order.setCreated(Calendar.getInstance().getTime());
		order.setState(OrderState.RECEIVED);
		orderDao.create(order);
		
		List<Order> orders = orderDao.findAll();
		//TODO
		Assert.assertEquals(orders.get(0).getState(), OrderState.RECEIVED);
		
	}
}
