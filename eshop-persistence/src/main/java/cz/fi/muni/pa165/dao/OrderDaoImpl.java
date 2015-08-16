package cz.fi.muni.pa165.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext 
	private EntityManager em;
	

	@Override
	public void create(Order order) {
		em.persist(order);
	}

	@Override public List<Order> findAll()
	{
		TypedQuery<Order> query = em.createQuery( "SELECT q FROM Order q", Order.class );
		return query.getResultList();
	}

	@Override
	public List<Order> findByUser(Long userId)
	{
		TypedQuery<Order> query = em.createQuery( "Select Order from Order o where o.user.id = ?1",Order.class );
		query.setParameter( 1, userId);
		return query.getResultList();
	}

	@Override
	public Order findById(Long id)
	{
		return em.find(Order.class, id);
	}

	@Override
	public void removeById(Long id)
	{
		em.remove(findById(id));
	}

	@Override
	public List<Order> getOrdersWithState(OrderState state)
	{
		TypedQuery<Order> query = em.createQuery( "SELECT o FROM Order o WHERE o.state = :state", Order.class );
		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public List<Order> getOrdersCreatedBetween(Date start, Date end, OrderState state)
	{
		TypedQuery<Order> query = em.createQuery( "SELECT o FROM Order e WHERE o.state = :state AND o.created BETWEEN :startDate AND :endDate ", Order.class );
		query.setParameter("startDate", start);
		query.setParameter("end", end);
		query.setParameter("state", state);
		return query.getResultList();
	}

}
