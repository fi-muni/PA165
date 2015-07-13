package cz.fi.muni.pa165.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.entity.User;
import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.entity.Order;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext 
	private EntityManager em;
	

	@Override
	public void create(Order order) {
		em.persist(order);
	}

//	@Override
//	public void update(Order order) {
//		em.merge(order);
//	}
//
//	@Override
//	public List<Order> findAll() {
//		return em.createQuery("select o from Order o", Order.class).getResultList();
//	}

	@Override
	public List<Order> findByUser(Long userId)
	{
		Query query = em.createQuery( "Select Order from Order o where o.user.id = ?1" );
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
	public List<Order> findAllWithState(OrderState state)
	{
		Query query = em.createQuery( "SELECT q FROM Order o WHERE o.state = :state", Order.class );
		query.setParameter("state", state);
		return query.getResultList();
	}

	@Override
	public List<Order> getOrdersCreatedBetween(Date start, Date end)
	{
		Query query = em.createQuery( "SELECT o FROM Order e WHERE o.created BETWEEN :startDate AND :endDate", Order.class );
		query.setParameter("startDate", start);
		query.setParameter("end", end);
		return query.getResultList();
	}

}
