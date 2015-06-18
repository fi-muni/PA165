package cz.fi.muni.pa165;

import java.util.Date;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import cz.fi.muni.pa165.entity.User;


@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDaoTest  extends AbstractTransactionalTestNGSpringContextTests
{

	/**
	 * Just helper method to create a valid user
	 * @return
	 */
	public static User getSimpleUser(){
		User user = new User();
		user.setEmail("filip@seznam.cz");
		user.setGivenName("Filip");
		user.setSurname("Markovic");
		user.setJoinedDate(new Date());
		return user;
	}
	
}