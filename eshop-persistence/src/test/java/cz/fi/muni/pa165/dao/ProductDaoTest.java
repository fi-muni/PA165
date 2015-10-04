package cz.fi.muni.pa165.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.CategoryDaoImpl;
import cz.fi.muni.pa165.dao.ProductDaoImpl;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.entity.Product.Color;
import cz.fi.muni.pa165.enums.Currency;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ProductDaoTest extends AbstractTestNGSpringContextTests {
	@PersistenceContext
	public EntityManager em;

	@Autowired
	public ProductDao productDao;

	@Autowired
	public PriceRepository priceRepository;

	@Autowired
	public CategoryDao categoryDao;

	private Product p1;
	private Product p2;
	private Product p3;
	private Product p4;
	private Product p5;

	@BeforeMethod
	public void createProducts() {
		p1 = new Product();
		p2 = new Product();
		p3 = new Product();
		p4 = new Product();
		p5 = new Product();

		Price priceLow = new Price();
		priceLow.setPriceStart(new Date());
		priceLow.setCurrency(Currency.CZK);
		priceLow.setValue(BigDecimal.TEN);
		priceRepository.save(priceLow);

		Category cat = new Category();
		cat.setName("cat");
		categoryDao.create(cat);

		p1.setName("p1");
		p2.setName("p2");
		p3.setName("product3");
		p4.setName("p4");
		p5.setName("p5");

		p1.addCategory(cat);
		p1.setColor(Color.RED);
		p1.setCurrentPrice(priceLow);
		p2.addCategory(cat);

		productDao.create(p1);
		productDao.create(p2);
		productDao.create(p3);
		productDao.create(p4);
		productDao.create(p5);
	}

	@Test
	public void findAll() {
		List<Product> found = productDao.findAll();
		Assert.assertEquals(found.size(), 5);
	}


	@Test
	public void findCategory() {
		Product found = productDao.findById(p1.getId());
		Assert.assertEquals(found.getCategories().size(), 1);
		Assert.assertEquals(found.getCategories().iterator().next().getName(), "cat");
	}

	
	@Test
	public void remove() {
		Assert.assertNotNull(productDao.findById(p3.getId()));
		productDao.remove(p3);
		Assert.assertNull(productDao.findById(p3.getId()));
	}

	@Test
	public void findByName() {
		Assert.assertEquals(productDao.findByName("p").size(), 5);
		Assert.assertEquals(productDao.findByName("asdf").size(), 0);
		Assert.assertEquals(productDao.findByName("product3").size(), 1);
	}

	@Test
	public void find() {
		Product found = productDao.findById(p1.getId());
		Assert.assertEquals(found.getName(), "p1");
		Assert.assertEquals(found.getColor(), Color.RED);
		Assert.assertEquals(found.getCurrentPrice().getValue(), BigDecimal.TEN);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void mimeTypeCannotBeSetWithoutImage() {
		Product p = new Product();
		p.setName("LCD TV");
		p.setImageMimeType("X");
		productDao.create(p);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void imageCannotBeSetWithoutMimeType() {
		Product p = new Product();
		p.setName("LCD TV");
		p.setImage(new byte[] {});
		productDao.create(p);
	}

	@Test
	public void imageCanBeSetWithMimeType() {
		Product p = new Product();
		p.setName("LCD TV");
		p.setImageMimeType("X");
		p.setImage(new byte[] {});
		productDao.create(p);
	}

}