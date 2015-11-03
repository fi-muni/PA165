package cz.fi.muni.pa165.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.User;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserDao userdao;

	private User u1 ;
	private User u2;

	@BeforeMethod
	public void createUsers() {
		u1 = new User();
		u2 = new User();

		u1.setGivenName("Filip");
		u1.setEmail("filip@fi.cz");
		u1.setJoinedDate(new Date());
		u1.setSurname("Filipovic");
		u1.setAddress("Brno");

		u2.setGivenName("Jirka");
		u2.setEmail("jirka@fi.cz");
		u2.setJoinedDate(new Date());
		u2.setSurname("Jirkovic");
        u2.setAddress("Praha");

		userdao.create(u1);
		userdao.create(u2);
	}

	@Test
	public void findByEmail() {
		Assert.assertNotNull(userdao.findUserByEmail("filip@fi.cz"));
	}

	@Test
	public void findByNonExistentEmail() {
		Assert.assertNull(userdao.findUserByEmail("asdfasdfasd"));
	}

	/**
	 * Just helper method to create a valid user
	 * 
	 * @return
	 */
	public static User getSimpleUser() {
		User user = new User();
		user.setEmail("filip@seznam.cz");
		user.setGivenName("Filip");
		user.setSurname("Markovic");
        user.setAddress("Jihlava");
		user.setJoinedDate(new Date());
		return user;
	}

	public static User getSimpleUser2() {
		User user = new User();
		user.setEmail("jirka@seznam.cz");
		user.setGivenName("Jiri");
		user.setSurname("Mrkev");
        user.setAddress("Hodonin");
		user.setJoinedDate(new Date());
		return user;
	}

}