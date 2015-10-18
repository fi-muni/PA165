package cz.fi.muni.pa165.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Product;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task03 extends AbstractTestNGSpringContextTests {

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void createFindDeleteTest(){
		Product p = new Product();
		p.setName("TestProduct1");
		productDao.create(p);
		
		//Solution begin
		Product p2 = new Product();
		p2.setName("TestProduct2");
		productDao.create(p2);
		
		Assert.assertEquals(productDao.findById(p.getId()).getName(),"TestProduct1");
		Assert.assertEquals(productDao.findAll().size(), 2);
		Assert.assertEquals(productDao.findByName("TestProduct1").size(), 1);
		productDao.remove(p2);
		Assert.assertEquals(productDao.findAll().size(), 1);
		//Solution end
	}
}
