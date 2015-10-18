package cz.fi.muni.pa165.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Product;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task03 extends AbstractTestNGSpringContextTests {

	//TODO uncomment after you have created the DAO
	@Autowired
//	private ProductDao productDao;
	
	@Test
	public void createFindDeleteTest(){
		Product p = new Product();
		p.setName("TestProduct1");
		//TODO uncomment after you have created the DAO
//		productDao.create(p);
		
		//TODO under this line
	}
}
