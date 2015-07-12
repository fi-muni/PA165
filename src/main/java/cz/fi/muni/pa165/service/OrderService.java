package cz.fi.muni.pa165.service;

import java.util.Collection;
import java.util.List;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.dto.OrderItemDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderItem;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.entity.User;

/**
 * An interface that defines a service access to the {@link Order} entity.
 */
public interface OrderService {
	/**
	 * Get all saved orders.
	 */
	Iterable<OrderDTO> getAllOrders();
	/**
	 * Get all saved orders belonging to the given user.
	 */
	Iterable<OrderDTO> getOrdersByUser(User u);
	/**
	 * Get all orders saved within last week that have the given state.
	 */
	Iterable<OrderDTO> getAllOrdersLastWeek(OrderState state);
	/**
	 * Get all orders saved within last week.
	 */
	Iterable<OrderDTO> getAllOrdersLastWeek();

	/**
	 * Get all orders with the given state.
	 */
	Iterable<OrderDTO> getAllOrders(OrderState state);

	/**
	 * Change the order state of a saved {@Order} for the provided {@link OrderDTO}
	 */
	void changeOrderState(OrderDTO o, OrderState state) throws IllegalArgumentException;

	/**
	 * Change the order price of a saved {@Order} for the provided {@link OrderDTO}
	 */
	void changeOrderItemPrice(OrderItemDTO item, Price newPrice);
	
}
