package cz.fi.muni.pa165;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.Product.Color;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.enums.OrderState;

public class DataPopulationListener implements ServletContextListener {
	
	private static Date date1;
	private static Date date2;
	private static Date o1created;
	
	static {
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 1,1);
		date1=cal.getTime();
		cal.set(2015,2,3);
		date2=cal.getTime();
		cal.set(2015,1,3,17,33,0);
		o1created=cal.getTime();
	}
	
	
	private void fillData(EntityManagerFactory emf){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Category cElectro = new Category();
		cElectro.setName("Electronics");
		em.persist(cElectro);
		
		User u = new User();
		u.setGivenName("Filip");
		u.setSurname("Filipovic");
		u.setEmail("filipovic@gmail.com");
		u.setJoinedDate(date1);
		u.setPhone("+420777444333");
		em.persist(u);
	
		Price priceTv = new Price();
		priceTv.setCurrency(Currency.CZK);
		priceTv.setPriceStart(date2);
		priceTv.setValue(BigDecimal.valueOf(5999));
		
		em.persist(priceTv);
		
		Product pTv = new Product();
		pTv.addCategory(cElectro);
		pTv.setCurrentPrice(priceTv);
		pTv.setColor(Color.WHITE);
		pTv.setName("Sharp TV");
		em.persist(pTv);
		
		OrderItem o1item1 = new OrderItem();
		o1item1.setAmount(1);
		o1item1.setProduct(pTv);
		o1item1.setPricePerItem(priceTv);
		em.persist(o1item1);
		
		Order o1=new Order();
		o1.setCreated(o1created);
		o1.setState(OrderState.RECEIVED);
		o1.setUser(u);
		o1.addOrderItem(o1item1);
		em.persist(o1);
		
		em.getTransaction().commit();
		em.close();		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		EntityManagerFactory emf = springContext.getBean("entityManagerFactory",
				EntityManagerFactory.class);
		fillData(emf);
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}