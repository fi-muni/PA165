package cz.fi.muni.pa165.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.OrderItemDao;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.entity.User;

/**
 * Implementation of the {@link OrderService}. This class is part of the service
 * module of the application that provides the implementation of the business
 * logic (main logic of the application).
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;


	@Override
	public Order findOrderById(Long id) {
		return orderDao.findById(id);
	}
	
	@Override
	public List<Order> getAllOrders(OrderState state) {
		return orderDao.getOrdersWithState(state);
	}

	@Override
	public List<Order> getOrdersByUser(User user) {
		return orderDao.findByUser(user.getId());
	}


	@Override
	public List<Order> getAllOrdersLastWeek(OrderState state) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date lastWeek = calendar.getTime();
		return orderDao.getOrdersCreatedBetween(lastWeek, now,state);
	}

	/**
	 * The only allowed changes of state are: RECIEVED - CANCELED RECEIVED -
	 * SHIPPED SHIPPED - DONE
	 */
	private Set<Transition> allowedTransitions = new HashSet<Transition>();
	{
		allowedTransitions.add(new Transition(OrderState.RECEIVED,
				OrderState.SHIPPED));
		allowedTransitions.add(new Transition(OrderState.RECEIVED,
				OrderState.CANCELED));
		allowedTransitions.add(new Transition(OrderState.SHIPPED,
				OrderState.DONE));
	}

	@Override
	public void shipOrder(Order order) {
		checkTransition(order.getState(), OrderState.SHIPPED);
		order.setState(OrderState.SHIPPED);
	}

	@Override
	public void finishOrder(Order order) {

		checkTransition(order.getState(), OrderState.DONE);
		order.setState(OrderState.DONE);

	}

	@Override
	public void cancelOrder(Order order) {
		checkTransition(order.getState(), OrderState.CANCELED);
		order.setState(OrderState.CANCELED);
	}

	private void checkTransition(OrderState oldState, OrderState newState) {
		if (!allowedTransitions.contains(new Transition(oldState, newState)))
			throw new OrderServiceException("The transition from: " + oldState
					+ " to " + newState + " is not allowed!");

	}

}
