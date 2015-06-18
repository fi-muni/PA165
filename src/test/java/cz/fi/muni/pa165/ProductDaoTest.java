package cz.fi.muni.pa165;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.dao.CategoryDaoImpl;
import cz.fi.muni.pa165.dao.ProductDaoImpl;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.User;


@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductDaoTest  extends AbstractTransactionalTestNGSpringContextTests
{
	@PersistenceContext
	public EntityManager em;
	
	@Autowired
	public ProductDaoImpl productDao;

	@Autowired
	public CategoryDaoImpl categoryDao;

	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void mimeTypeCannotBeSetWithoutImage(){
		Product p  = new Product();
		p.setName("LCD TV");
		p.setImageMimeType("X");
		productDao.create(p);
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void imageCannotBeSetWithoutMimeType(){
		Product p  = new Product();
		p.setName("LCD TV");
		p.setImage(new byte[]{});
		productDao.create(p);
	}
	
	@Test	
	public void imageCanBeSetWithMimeType(){
		Product p  = new Product();
		p.setName("LCD TV");
		p.setImageMimeType("X");
		p.setImage(new byte[]{});
		productDao.create(p);
	}

}