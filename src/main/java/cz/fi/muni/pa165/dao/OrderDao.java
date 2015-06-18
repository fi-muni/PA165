package cz.fi.muni.pa165.dao;

import java.util.List;

import cz.fi.muni.pa165.entity.Order;

public interface OrderDao {
	public void create(Order order);
	public void update(Order order);
	public List<Order> findAll();
}
