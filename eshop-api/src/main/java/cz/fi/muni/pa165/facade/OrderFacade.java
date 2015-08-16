package cz.fi.muni.pa165.facade;

import java.util.List;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.enums.OrderState;

public interface OrderFacade {
	List<OrderDTO> getOrdersByUser(Long userId);
	List<OrderDTO> getAllOrdersLastWeek(OrderState state);
	List<OrderDTO> getAllOrders(OrderState state);
	List<OrderDTO> getAllOrders();
	OrderDTO getOrderById(Long id);
	void shipOrder(Long id);
	void finishOrder(Long id);
	void cancelOrder(Long id);
}
