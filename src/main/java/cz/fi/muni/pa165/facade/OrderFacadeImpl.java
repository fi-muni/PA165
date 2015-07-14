package cz.fi.muni.pa165.facade;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.service.OrderService;
import cz.fi.muni.pa165.service.UserService;

@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade{
    
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private OrderService orderService;
    

    @Autowired
    private UserService userService;
    
    
    private List<OrderDTO> mapToDTO(List<Order> orders) {
        List<OrderDTO> mappedCollection = new ArrayList<>();
        for (Order order : orders) {
            mappedCollection.add(dozerBeanMapper.map(order, OrderDTO.class));
        }
        return mappedCollection;
    }

	@Override
	public List<OrderDTO> getOrdersByUser(Long userId) {
		User user = userService.findUserById(userId);
		List<Order> orders = orderService.getOrdersByUser(user);
		
		return mapToDTO(orders);
	}

	@Override
	public List<OrderDTO> getAllOrdersLastWeek(OrderState state) {
		return mapToDTO(orderService.getAllOrdersLastWeek(state));
	}

	@Override
	public List<OrderDTO> getAllOrders(OrderState state) {
		return mapToDTO(orderService.getAllOrders(state));
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
