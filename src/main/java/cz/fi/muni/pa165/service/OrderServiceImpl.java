package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dao.OrderItemDao;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.dto.OrderItemDTO;
import cz.fi.muni.pa165.entity.*;
import org.dozer.DozerBeanMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the {@link OrderService}. This class is part of the service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 */
public class OrderServiceImpl implements OrderService
{
    @Inject
    private OrderDao orderDao;
    @Inject
    private OrderItemDao orderItemDao;
    private DozerBeanMapper dozerBeanMapper;

    public Iterable<OrderDTO> getAllOrders() {
        return mapToDTO(orderDao.findAll());
    }

    public void createOrder(OrderDTO order) {
         orderDao.create(dozerBeanMapper.map(order, Order.class));
    }
    public Iterable<OrderDTO> getOrdersByUser(User u){
            return mapToDTO(orderDao.findByUser(u));
    }
    public Iterable<OrderDTO> getAllOrdersLastWeek(OrderState state){
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
    public Iterable<OrderDTO> getAllOrders(OrderState state){
        return mapToDTO(orderDao.findAllWithState(state));
    }
    public void changeOrderState(OrderDTO o, OrderState state){
        Order foundOrder = orderDao.findById(o.getId());
        if(foundOrder != null) {
            orderDao.removeById(o.getId());
            foundOrder.setState(state);
            orderDao.create(foundOrder);
        } else {
            throw new IllegalArgumentException("Order with ID " + o.getId() + " is not saved in the database. It's state cannot be changed.");
        }

    }

    @Inject
    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public void changeOrderItemPrice(OrderItemDTO item, Price newPrice){
        OrderItem foundOrder = orderItemDao.findById(item.getId());
        if(foundOrder != null) {
            orderItemDao.removeById(item.getId());
            foundOrder.setPricePerItem(newPrice);
            orderItemDao.create(foundOrder);
        } else {
            throw new IllegalArgumentException("OrderItem with ID " + item.getId() + " is not saved in the database. It's price cannot be changed.");
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
