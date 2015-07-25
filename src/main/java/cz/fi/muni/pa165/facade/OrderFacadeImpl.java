package cz.fi.muni.pa165.facade;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.pa165.dto.UserDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.UserService;

@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade{
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

	@Override
	public List<OrderDTO> getOrdersByUser(Long userId) {
		User user = userService.findUserById(userId);
		List<Order> orders = orderService.getOrdersByUser(user);
		
		return FacadeUtils.mapTo(orders, OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getAllOrdersLastWeek(OrderState state) {
		final List<Order> allOrdersLastWeek = orderService.getAllOrdersLastWeek(state);
		final List<OrderDTO> dtos= FacadeUtils.mapTo(allOrdersLastWeek, OrderDTO.class);
		return dtos;
	}

	@Override
	public List<OrderDTO> getAllOrders(OrderState state) {
		return FacadeUtils.mapTo(orderService.getAllOrders(state), OrderDTO.class);
	}

	@Override
	public void shipOrder(Long id) {
		orderService.shipOrder(orderService.findOrderById(id));
	}

	@Override
	public void finishOrder(Long id) {
		orderService.finishOrder(orderService.findOrderById(id));
	}

	@Override
	public void cancelOrder(Long id) {
		orderService.cancelOrder(orderService.findOrderById(id));
	}
}
