package cz.fi.muni.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.ProductComment;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext 
	private EntityManager em;
	

	@Override
	public void create(Order order) {
		em.persist(order);
	}

	@Override
	public void update(Order order) {
		em.merge(order);
	}

	@Override
	public List<Order> findAll() {
		return em.createQuery("select o from Order o", Order.class).getResultList();
	}

}
