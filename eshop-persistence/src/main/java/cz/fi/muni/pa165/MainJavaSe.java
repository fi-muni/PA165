package cz.fi.muni.pa165;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

public class MainJavaSe {
	private static EntityManagerFactory emf;
	
	public static void main(String[] args) throws SQLException {
		//The following line is here just to start up a in-memory database 
		new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);
		
		System.out.println(" ****** STARTING PET STORE APPLICATOIN ****** ");
		emf = Persistence.createEntityManagerFactory("javaSeUnit");
		
		// BEGIN YOUR CODE
		task06();
		// END YOUR CODE
		emf.close();
	}
	
	private static void task06() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category category = new Category();
		category.setName("Electronics");
		em.persist(category);
		em.getTransaction().commit();
		em.close();
		
		//TODO under this line. create new EM and start new transaction. Merge the detached category
		//into the context and change the name to "Electro" 
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		category = em2.merge(category);
		category.setName("Electro");
		em2.getTransaction().commit();
		em2.close();
		
		
		EntityManager checkingEm= emf.createEntityManager();
		checkingEm.getTransaction().begin();
		Category cat = checkingEm.find(Category.class, category.getId());
		assertEq(cat.getName(), "Electro");
		System.out.println("Name changed successfully to Electro");
		checkingEm.getTransaction().commit();
		checkingEm.close();
	}

	private static void task05() {
		//TODO under this line, persist two categories, one with name Electronics and second with name Musical
		
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Category> categories = em.createQuery("select c from Category c order by c.name", Category.class).getResultList();
		
		assertEq(categories.get(0).getName(),"Electronics");
		assertEq(categories.get(1).getName(),"Musical");
		

		em.getTransaction().commit();
		em.close();
		
		System.out.println("Succesfully found Electronics and Musical!");
	}

	private static void assertEq(String str1, String str2) {
		if (!str1.equals(str2)){
			throw new RuntimeException("Expected these two strings to be identical: "+ str1+", "+ str2);
		}
	}
}
