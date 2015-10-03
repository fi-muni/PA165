package cz.fi.muni.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryDaoTest  extends AbstractTestNGSpringContextTests{

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@PersistenceContext 
	private EntityManager em;
	
	@Test
	public void findAll(){
		Category cat1 = new Category();
		Category cat2 = new Category();
		cat1.setName("cat1");
		cat2.setName("cat2");
		
		categoryDao.create(cat1);
		categoryDao.create(cat2);
		
		List<Category> categories  = categoryDao.findAll();
		
		Assert.assertEquals(categories.size(), 2);
		
		Category cat1assert = new Category();
		Category cat2assert = new Category();
		cat1assert.setName("cat1");
		cat2assert.setName("cat2");
		
		Assert.assertTrue(categories.contains(cat1assert));
		Assert.assertTrue(categories.contains(cat2assert));
		
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void nullCategoryNameNotAllowed(){
		Category cat = new Category();
		cat.setName(null);
		categoryDao.create(cat);		
	}
	
	@Test(expectedExceptions=PersistenceException.class)
	public void nameIsUnique(){
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);	
		cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
	}
	
	@Test()
	public void savesName(){
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
		Assert.assertEquals(categoryDao.findById(cat.getId()).getName(),"Electronics");
	}
	
	/**
	 * Checks that null DAO object will return null for non existent ID and also that delete operation works.
	 */
	@Test()
	public void delete(){
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
		Assert.assertNotNull(categoryDao.findById(cat.getId()));
		categoryDao.delete(cat);
		Assert.assertNull(categoryDao.findById(cat.getId()));
	}
	
	/**
	 * Testing that collections on Category is being loaded as expected
	 */
	@Test
	public void productsInCategory(){
		Category categoryElectro = new Category();
		categoryElectro.setName("Electronics");
		categoryDao.create(categoryElectro);			
		Product p = new Product();
		p.setName("TV");
		productDao.create(p);
		p.addCategory(categoryElectro);		
		Category found = categoryDao.findById(categoryElectro.getId());
		Assert.assertEquals(found.getProducts().size(), 1);
		Assert.assertEquals(found.getProducts().iterator().next().getName(), "TV");
	}
}
