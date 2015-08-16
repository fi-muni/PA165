package cz.fi.muni.pa165.dao;

import java.util.Date;
import java.util.List;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;

public interface OrderDao  {
	public void create(Order order);
	public List<Order> findAll();
	public List<Order> findByUser(Long id);
	public Order findById(Long id);
	public void removeById(Long id)  throws IllegalArgumentException;
	List<Order> getOrdersWithState(OrderState state);
	List<Order> getOrdersCreatedBetween(Date start, Date end, OrderState state);
	
}
