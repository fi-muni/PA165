package cz.fi.muni.pa165.tasks;

import cz.fi.muni.pa165.seminarservices.SeminarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Product;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task03 extends AbstractTestNGSpringContextTests {

	//TODO uncomment after you have created the DAO Impl
//	@Autowired
//	private SeminarServiceImpl productService;
	
//	@Test
//	public void createFindDeleteTest(){
//		Product p = new Product();
//		p.setName("TestProduct1");
//
//		productService.create(p);
//
//		Product p2 = new Product();
//		p2.setName("TestProduct2");
//		productService.create(p2);
//
//		Assert.assertEquals(productService.findById(p.getId()).getName(),"TestProduct1");
//		Assert.assertEquals(productService.findAll().size(), 2);
//		Assert.assertEquals(productService.findByName("TestProduct1").size(), 1);
//		productService.remove(p2);
//		Assert.assertEquals(productService.findAll().size(), 1);
//	}
}
