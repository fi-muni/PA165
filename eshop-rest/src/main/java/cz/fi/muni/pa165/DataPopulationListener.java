package cz.fi.muni.pa165;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cz.fi.muni.pa165.entity.Category;

public class DataPopulationListener implements ServletContextListener {
	
	private void fillData(EntityManagerFactory emf){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Category c = new Category();
		c.setName("MojeKategorie");
		em.persist(c);
		
		em.getTransaction().commit();
		em.close();		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		EntityManagerFactory emf = springContext.getBean("entityManagerFactory",
				EntityManagerFactory.class);
		fillData(emf);
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}