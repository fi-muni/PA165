package cz.fi.muni.pa165.facade;

import java.util.List;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.enums.OrderState;

public interface OrderFacade {
	public List<OrderDTO> getOrdersByUser(Long userId);
	public List<OrderDTO> getAllOrdersLastWeek(OrderState state);
	public List<OrderDTO> getAllOrders(OrderState state);
	public List<OrderDTO> getAllOrders();
	public OrderDTO getOrderById(Long id);
	public void shipOrder(Long id);
	public void finishOrder(Long id);
	public void cancelOrder(Long id);
	public List<OrderDTO> getOrdersByState(OrderState valueOf);
}
