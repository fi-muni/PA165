package cz.fi.muni.pa165.tasks;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task04 extends AbstractTestNGSpringContextTests {

	@PersistenceContext
	private EntityManager em;
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@BeforeClass
	public void setup(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date today = new Date();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		
		EntityManager e=emf.createEntityManager();
		e.getTransaction().begin();
		
		Category electro = new Category();
		electro.setName("Electro");
		Category kitchen = new Category();
		kitchen.setName("Kitchen");
		e.persist(electro);
		e.persist(kitchen);
		
		Product plate = new Product();
		plate.setName("Plate");
		plate.setColor(Color.WHITE);
		plate.addCategory(kitchen);
		plate.setAddedDate(yesterday);
		
		Product fork= new Product();
		fork.setName("Fork");
		fork.setColor(Color.WHITE);
		fork.addCategory(kitchen);
		fork.setAddedDate(today);
		
		Product kitchenRobot= new Product();
		kitchenRobot.setName("Kitchen Robot");
		kitchenRobot.setColor(Color.WHITE);
		kitchenRobot.addCategory(kitchen);
		kitchenRobot.addCategory(electro);
		kitchenRobot.setAddedDate(today);
		
		Product flashlight= new Product();
		flashlight.setName("Flashlight");
		flashlight.setColor(Color.RED);
		flashlight.setAddedDate(today);
		
		e.persist(plate);
		e.persist(fork);
		e.persist(kitchenRobot);
		e.persist(flashlight);
		
		
		e.getTransaction().commit();
		e.close();
		
	}
	
	/**
	 * Find all products
	 * 
	 */
	@Test
	public void findProducts(){
		List<Product> found = em.createQuery("TODO",Product.class).getResultList();
		Assert.assertEquals(found.size(), 4);
	}
	
	/**
	 * Find product with name 'Flashlight'. Make sure to use named parameter, e.g. using semicolon :name and setParameter method on Query object
	 */
	@Test
	public void findProductByName(){
		List<Product> found = em.createQuery("TODO",Product.class).setParameter("name", "Flashlight").getResultList();
		Assert.assertEquals(found.size(), 1);
		Assert.assertEquals(found.get(0).getName(), "Flashlight");
		Assert.assertEquals(found.get(0).getColor(), Color.RED);
	}
	

	/**
	 * Write query that returns count of Products, make sure to return only 1 number
	 */
	@Test
	public void countProducts() {
		Long count = em.createQuery("TODO",Long.class).getSingleResult();
			
		Assert.assertEquals(count, new Long(4));
	}
	
	/**
	 * Find all products that have some categories assigned (tip: use IS NOT EMPTY operator) 
	 */
	@Test
	public void findProductsWithNonEmtpycategory() {
		List<Product> found = em.createQuery("TODO",Product.class).getResultList();

		Assert.assertEquals(found.size(), 3);
	}
	
	/**
	 * Find all products and eagerly fetch all categories. You must use JOIN FETCH clause
	 */
	@Test
	public void findProductsWithCategories(){
		List<Product> found = em.createQuery("TODO",Product.class).getResultList();
		
		Assert.assertEquals(found.size(), 4);
		//The following will throw exception in case the categories are not fetched
		for (Product product : found)
			System.out.println(product.getCategories().size());
	}
	
	/**
	 * Find count of Products per Color. This means that the result should be List of Object[] objects
	 * where each Object[] x is in the following format: 
	 *   x[0] = Color
	 *   x[1] = Long
	 *   
	 *  To do this you need to use Group by and also use ORDER BY to order it by Color. 
	 */
	@Test
	public void groupByAndOrderBy(){
		List<Object[]> found= em.createQuery("TODO", Object[].class).getResultList();
		
		Assert.assertEquals(found.size(), 2);
		Assert.assertEquals(((Color) found.get(0)[0]), Color.RED);
		Assert.assertEquals(((Long) found.get(0)[1]), new Long(1));
		Assert.assertEquals(((Color) found.get(1)[0]), Color.WHITE);
		Assert.assertEquals(((Long) found.get(1)[1]), new Long(3));
	}
	
	/**
	 * Use SELECT NEW to construct ColorCount objects directly in the query. The query will be almost the same
	 * as in the previous test groupByAndOrderBy only additional thing here is using of the SELECT NEW. 
	 */
	@Test
	public void groupByAndOrderBySelectNew() {
		List<ColorCount> colorCounts = em.createQuery("TODO",ColorCount.class).getResultList();
		
		Assert.assertEquals(colorCounts.get(0).getColor(), Color.RED);
		Assert.assertEquals(colorCounts.get(0).getCount(), new Long(1));
		Assert.assertEquals(colorCounts.get(1).getColor(), Color.WHITE);
		Assert.assertEquals(colorCounts.get(1).getCount(), new Long(3));
	}
	
	/**
	 * Find all products that has addedDate yesterday
	 */
	@Test
	public void findProductsAddedYesterday() {
		Calendar cal  = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		List<Product> products= em.createQuery("TODO",Product.class).setParameter("date", cal.getTime()).getResultList();
		
		Assert.assertEquals(products.size(),1);
		Assert.assertEquals(products.get(0).getName(), "Plate");
	}
	
	/**
	 * 
	 * Now using SELECT NEW create ProductAndCategory objects. Use LEFT JOIN with ON keyword. Join only those tuples where category name is Kitchen.
	 * 
	 * This is very interesting task because it should help you undrestand how ON keyword differes from standard WHERE.
	 * he LEFT JOIN semantics together with ON keyword is fairly complicated. See JPA spec section 4.4.5.2
	 */
	@Test
	public void leftJoinCagesWithPets() {
		EntityManager em = emf.createEntityManager();
		List<ProductAndCategory> productAndCategory = em.createQuery("TODO",ProductAndCategory.class).getResultList();
		
		Assert.assertEquals(productAndCategory.size(), 5);
		
		Assert.assertEquals(productAndCategory.get(0).getProduct().getName(),"Flashlight");
		Assert.assertNull(productAndCategory.get(0).getCategory());
		
		Assert.assertEquals(productAndCategory.get(1).getProduct().getName(),"Fork");
		Assert.assertEquals(productAndCategory.get(1).getCategory().getName(), "Kitchen");		
		
		Assert.assertEquals(productAndCategory.get(2).getProduct().getName(),"Kitchen Robot");
		Assert.assertEquals(productAndCategory.get(2).getCategory().getName(), "Kitchen");	

		Assert.assertEquals(productAndCategory.get(3).getProduct().getName(),"Kitchen Robot");
		Assert.assertNull(productAndCategory.get(3).getCategory());

		Assert.assertEquals(productAndCategory.get(4).getProduct().getName(),"Plate");
		Assert.assertEquals(productAndCategory.get(4).getCategory().getName(), "Kitchen");	
	}
	
	/**
	 * Find all products using criteria API,
	 * see http://docs.oracle.com/javaee/6/tutorial/doc/gjrij.html
	 */
	@Test
	public void criteriaFindAll(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> query = cb.createQuery(Product.class);
		//TODO under this line create a Root<Product> instance and then use .select() method on this instance
		
		
		List<Product> found = em.createQuery(query).getResultList();
		Assert.assertEquals(found.size(), 4);
	}
	
	/**
	 * Hint: use CriteriaBuilder.isNotEmpty in where() method
	 * 
	 * You will also need method Root.get("....")
	 */
	@Test
	public void criteriaFindProductsWithNonEmptyCategory() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> query = cb.createQuery(Product.class);
		//TODO under this line create a Root<Product> instance and then use .select() method on this instance and .where on this instance
		//content of where should use CriteriaBuilder.isNotEmpty method
		
		List<Product> found = em.createQuery(query).getResultList();
		Assert.assertEquals(found.size(), 3);
	}
}
