package cz.fi.muni.pa165.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.OrderState;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Order order) {
		em.persist(order);
	}

	@Override
	public List<Order> findAll() {
		TypedQuery<Order> query = em.createQuery("SELECT q FROM Order q",
				Order.class);
		return query.getResultList();
	}

	@Override
	public List<Order> findByUser(User u) {
		TypedQuery<Order> query = em.createQuery(
				"Select o from Order o where o.user = :userid",
				Order.class);
		
		query.setParameter("userid", u);
		return query.getResultList();
	}

	@Override
	public Order findById(Long id) {
		return em.find(Order.class, id);
	}

	@Override
	public void remove(Order o) {
		em.remove(o);
	}

	@Override
	public List<Order> getOrdersWithState(OrderState state) {
		TypedQuery<Order> query = em.createQuery(
				"SELECT o FROM Order o WHERE o.state = :state", Order.class);
		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public List<Order> getOrdersCreatedBetween(Date start, Date end,
			OrderState state) {
		TypedQuery<Order> query = em
				.createQuery(
						"SELECT o FROM Order o WHERE o.state = :state AND  o.created BETWEEN :startDate AND :endDate ",
						Order.class);
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		query.setParameter("state", state);
		return query.getResultList();
	}

}
