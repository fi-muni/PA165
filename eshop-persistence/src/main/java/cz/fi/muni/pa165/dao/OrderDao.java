package cz.fi.muni.pa165.dao;

import java.util.Date;
import java.util.List;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.OrderState;

public interface OrderDao  {
	public void create(Order order);
	public List<Order> findAll();
	public List<Order> findByUser(User u);
	public Order findById(Long id);
	public void remove(Order o)  throws IllegalArgumentException;
	public List<Order> getOrdersWithState(OrderState state);
	public List<Order> getOrdersCreatedBetween(Date start, Date end, OrderState state);
	
}
