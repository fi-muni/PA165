package cz.fi.muni.pa165.service.facade;

import java.util.List;

import cz.fi.muni.pa165.dto.OrderTotalPriceDTO;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.facade.OrderFacade;
import cz.fi.muni.pa165.service.BeanMappingService;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.UserService;

@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private BeanMappingService beanMappingService;

	@Override
	public List<OrderDTO> getOrdersByUser(Long userId) {
		User user = userService.findUserById(userId);
		List<Order> orders = orderService.getOrdersByUser(user);

		return beanMappingService.mapTo(orders, OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getAllOrdersLastWeek(OrderState state) {
		final List<Order> allOrdersLastWeek = orderService
				.getAllOrdersLastWeek(state);
		final List<OrderDTO> dtos = beanMappingService.mapTo(allOrdersLastWeek,
				OrderDTO.class);
		return dtos;
	}

	@Override
	public List<OrderDTO> getOrdersByState(OrderState state) {
		return beanMappingService.mapTo(orderService.getOrdersByState(state),
				OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		return beanMappingService.mapTo(orderService.findAllOrders(),
				OrderDTO.class);
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		return beanMappingService.mapTo(orderService.findOrderById(id),
				OrderDTO.class);
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

	@Override
	public OrderTotalPriceDTO getOrderTotalPrice(long id, Currency currency) {
		OrderTotalPriceDTO otp = new OrderTotalPriceDTO();
		otp.setOrder(this.getOrderById(id));
		Price totalPrice = orderService.getTotalPrice(id,currency);
		otp.setPrice(totalPrice.getValue());
		otp.setCurrency(totalPrice.getCurrency());
		return otp;
	}


}
