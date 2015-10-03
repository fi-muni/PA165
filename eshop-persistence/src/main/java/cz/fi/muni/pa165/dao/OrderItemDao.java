package cz.fi.muni.pa165.dao;

import java.util.List;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;

public interface OrderItemDao {
	public OrderItem findById(Long id);
	public void removeById(Long id);
	public void delete(OrderItem order);
}
