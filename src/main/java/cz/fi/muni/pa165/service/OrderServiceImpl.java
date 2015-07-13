package cz.fi.muni.pa165.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.OrderItemDao;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;

/**
 * Implementation of the {@link OrderService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */
@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private OrderItemDao orderItemDao;
    
    @Autowired
    private DozerBeanMapper dozerBeanMapper;

	@Override
	public List<OrderDTO> getAllOrders(OrderState state) {
		return mapToDTO(orderDao.findAllWithState(state));
	}

    public void createOrder(OrderDTO order) {
         orderDao.create(dozerBeanMapper.map(order, Order.class));
    }
    
    public List<OrderDTO> getOrdersByUser(Long userId){
            return mapToDTO(orderDao.findByUser(userId));
    }
    
    public List<OrderDTO> getAllOrdersLastWeek(OrderState state){
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        for (OrderDTO order : this.getAllOrdersLastWeek())
        {
            if(order.getState().equals(state))
                orders.add(order);
        }
        return orders;
    }
    
    public Iterable<OrderDTO> getAllOrdersLastWeek(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeek = calendar.getTime();
        return mapToDTO(orderDao.getOrdersCreatedBetween(lastWeek,now));
    }
    
    Set<Transition> allowedTransitions = new HashSet<Transition>();
    {
    	allowedTransitions.add(new Transition(OrderState.RECEIVED, OrderState.SHIPPED));
    	allowedTransitions.add(new Transition(OrderState.RECEIVED, OrderState.CANCELED));
    	allowedTransitions.add(new Transition(OrderState.SHIPPED, OrderState.DONE));
    }
    /**
     * The only allowed changes of state are:
     *   RECIEVED - CANCELED
     *   RECEIVED - SHIPPED
     *   SHIPPED - DONE
     */
    @Override
    public void changeOrderState(Long id, OrderState newState){
    	Order foundOrder = orderDao.findById(id);
    	
        if(foundOrder != null) {
        	if (!allowedTransitions.contains(new Transition(foundOrder.getState(), newState)))
        		throw new OrderServiceException("The transition from: "+ foundOrder.getState()+ " to "+ newState+" is not allowed!");
        	foundOrder.setState(newState);
        } else {
            throw new IllegalArgumentException("Order with ID " + id  + " is not saved in the database. It's state cannot be changed.");
        }

    }

    private List<OrderDTO> mapToDTO(List<Order> orders) {
        List<OrderDTO> mappedCollection = new ArrayList<>();
        for (Order order : orders) {
            mappedCollection.add(dozerBeanMapper.map(order, OrderDTO.class));
        }
        return mappedCollection;
    }


}
