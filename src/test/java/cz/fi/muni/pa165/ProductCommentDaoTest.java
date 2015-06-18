package cz.fi.muni.pa165;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.dao.ProductCommentDao;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.Product;
import cz.fi.muni.pa165.entity.ProductComment;
import cz.fi.muni.pa165.entity.User;


@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductCommentDaoTest  extends AbstractTransactionalTestNGSpringContextTests
{
	@PersistenceContext
	public EntityManager em;
	
	@Autowired
	public ProductDao productDao;

	@Autowired
	public UserDao userDao;

	@Autowired
	public ProductCommentDao productCommentDao;
	
	
	@Test
	public void userAndProductGetComment(){
		User u= UserDaoTest.getSimpleUser();
		userDao.create(u);
		
		Product p = new Product();
		p.setName("LG TV");
		
		productDao.create(p);
		
		ProductComment comment = new ProductComment();
		comment.setText("Ahoj!");
		comment.setProduct(p);
		comment.setUser(u);
		comment.setCreated(new Date());
		
		productCommentDao.create(comment);
		
		em.flush();
		em.clear();
		
		User foundUser = userDao.findUserByEmail(u.getEmail());
		Product foundProduct = productDao.findById(p.getId());
		
		Assert.assertEquals(foundUser.getComments().get(0), comment);
		Assert.assertEquals(foundProduct.getComments().get(0), comment);
		
	}
	
}