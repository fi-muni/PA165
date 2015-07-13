package cz.fi.muni.pa165.service;

import java.util.List;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;

/**
 * An interface that defines a service access to the {@link Order} entity.
 */
public interface OrderService {
	
	/**
	 * Get all saved orders belonging to the given user.
	 */
	List<OrderDTO> getOrdersByUser(Long userId);
	
	/**
	 * Get all orders saved within last week that have the given state.
	 */
	List<OrderDTO> getAllOrdersLastWeek(OrderState state);

	/**
	 * Get all orders with the given state.
	 */
	List<OrderDTO> getAllOrders(OrderState state);

	/**
	 * Change the order state of a saved {@Order} for the provided {@link OrderDTO}
	 */
	void changeOrderState(Long id, OrderState newState) throws IllegalArgumentException;
}
