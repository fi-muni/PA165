package cz.fi.muni.pa165.service;

import java.util.List;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.User;

public interface OrderService {
	List<Order> getAllOrders();
	List<Order> getOrdersByUser(User u);
	List<Order> getAllOrdersLastWeek(OrderState state);
	List<Order> getAllOrdersLastWeek();
	List<Order> getAllOrders(OrderState state);
	void changeOrderState(Order o, OrderState state);
	
	void changeOrderItemPrice(OrderItem item, Price newPrice);
	
}
