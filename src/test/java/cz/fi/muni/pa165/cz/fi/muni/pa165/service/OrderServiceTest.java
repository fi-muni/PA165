package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.OrderDao;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.OrderState;
import cz.fi.muni.pa165.service.OrderServiceImpl;
import org.dozer.DozerBeanMapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest
{

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    Order order;
    OrderDTO orderDTO;

    @Before
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);

        List<String> myMappingFiles = new ArrayList<>();
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(myMappingFiles);

        orderService.setDozerBeanMapper(mapper);

        order = new Order();
        Date created = new Date();
        order.setCreated(created);
        order.setState(OrderState.CANCELED);

        orderDTO = new OrderDTO();
        orderDTO.setCreated(created);
        orderDTO.setState(OrderState.CANCELED);

        when(orderDao.findById(1l)).thenReturn(order);
        when(orderDao.findById(2l)).thenReturn(null);
    }

    @Test
    public void testCreateOrderTransformsToDTO()
    {
        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        orderService.createOrder(orderDTO);
        verify(orderDao).create(argumentCaptor.capture());
        isEqual(order, argumentCaptor.getValue());
    }

    private void isEqual(Order order, Order other)
    {
        if(!order.getId().equals(other.getId())) {
            Assert.fail("IsEqual fail: 1. order id(" + order.getId() + ") and 2. order id(" + other.getId() + ") are not equal.");
        }
        if(!order.getState().equals(other.getState())) {
            Assert.fail("IsEqual fail: 1. order state(" +order.getState() + ") and 2. order state(" +other.getState() + ") are not equal." );
        }
        if(!order.getCreated().equals(other.getCreated())) {
            Assert.fail("IsEqual fail: 1. order state(" +order.getCreated() + ") and 2. order state(" +other.getCreated() + ") are not equal." );
        }

    }

}