package cz.fi.muni.pa165.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public List<Order> findAll() {
		return em.createQuery("select o from OrderItem o", Order.class).getResultList();
	}

	@Override
	public void create(OrderItem orderItem) {
		em.persist(orderItem);
	}

	@Override
	public void update(OrderItem orderItem) {
		em.merge(orderItem);
	}

	@Override
	public void delete(OrderItem orderItem) {
		em.remove(orderItem);
	}

}
