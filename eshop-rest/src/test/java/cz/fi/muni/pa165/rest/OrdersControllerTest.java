package cz.fi.muni.pa165.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.RootWebContext;
import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.facade.OrderFacade;
import cz.fi.muni.pa165.rest.controllers.OrdersController;
import static org.mockito.Mockito.doThrow;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class OrdersControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private OrderFacade orderFacade;

    @Autowired
    @InjectMocks
    private OrdersController ordersController;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(ordersController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getAllOrders() throws Exception {

        doReturn(Collections.unmodifiableList(this.createOrders())).when(orderFacade).getAllOrders();

        mockMvc.perform(get("/orders").param("status", "ALL")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].state").value("DONE"))
                .andExpect(jsonPath("$.[?(@.id==2)].state").value("CANCELED"))
                .andExpect(jsonPath("$.[?(@.id==6)].state").value("SHIPPED"));

    }

    @Test
    public void getAllOrdersByState() throws Exception {

        List<OrderDTO> orders = this.createOrders().stream()
           .filter(o -> o.getState().equals("DONE")).collect(Collectors.toList());

        doReturn(Collections.unmodifiableList(orders)).when(orderFacade).getOrdersByState(OrderState.DONE);

        mockMvc.perform(get("/orders").param("status", "ALL")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].state").value("DONE")); 

    }
    
    @Test
    public void getAllOrdersByUserId() throws Exception {

        doReturn(Collections.unmodifiableList(this.createOrders())).when(orderFacade).getOrdersByUser(1L);

        mockMvc.perform(get("/orders/by_user_id/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].state").value("DONE"))
                .andExpect(jsonPath("$.[?(@.id==2)].state").value("CANCELED"))
                .andExpect(jsonPath("$.[?(@.id==6)].state").value("SHIPPED"));

    }
    
    @Test
    public void getValidOrder() throws Exception {

        List<OrderDTO> orders = this.createOrders();

        doReturn(orders.get(0)).when(orderFacade).getOrderById(1L);
        doReturn(orders.get(1)).when(orderFacade).getOrderById(2L);
        doReturn(orders.get(2)).when(orderFacade).getOrderById(3L);
        doReturn(orders.get(3)).when(orderFacade).getOrderById(4L);
        doReturn(orders.get(4)).when(orderFacade).getOrderById(5L);
        doReturn(orders.get(5)).when(orderFacade).getOrderById(6L);
        
    
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.state").value("DONE"));

        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.state").value("CANCELED"));
        
        mockMvc.perform(get("/orders/6"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.state").value("SHIPPED"));

    }

     @Test
    public void getInvalidOrder() throws Exception {
        doThrow(new RuntimeException()).when(orderFacade).getOrderById(1l);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void shipOrder() throws Exception {
        
        List<OrderDTO> orders = this.createOrders();

        doReturn(orders.get(0)).when(orderFacade).getOrderById(1L);
        doNothing().when(orderFacade).shipOrder(1L);
        
        mockMvc.perform(post("/orders/1").param("action", "SHIP")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1));   // TODO: improve this test

    }
    
    @Test
    public void shipOrderInvalidAction() throws Exception {
        
        List<OrderDTO> orders = this.createOrders();

        doReturn(orders.get(0)).when(orderFacade).getOrderById(1L);
        doNothing().when(orderFacade).shipOrder(1L);
        
        mockMvc.perform(post("/orders/1").param("action", "INVALID")).andDo(print())
                .andExpect(status().isNotAcceptable());

    }
    
    
    private List<OrderDTO> createOrders() {
        
        OrderDTO orderOne = new OrderDTO();
        orderOne.setId(1l);
        orderOne.setState(OrderState.DONE);
        orderOne.setCreated(Calendar.getInstance().getTime());

        OrderDTO orderTwo = new OrderDTO();
        orderTwo.setId(2l);
        orderTwo.setState(OrderState.CANCELED);
        orderTwo.setCreated(Calendar.getInstance().getTime());

        
        OrderDTO orderThree = new OrderDTO();
        orderThree.setId(3l);
        orderThree.setState(OrderState.RECEIVED);
        orderThree.setCreated(Calendar.getInstance().getTime());
        
        OrderDTO orderFour = new OrderDTO();
        orderFour.setId(4l);
        orderFour.setState(OrderState.SHIPPED);
        orderFour.setCreated(Calendar.getInstance().getTime());

        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);    
        
        OrderDTO orderFive = new OrderDTO();
        orderFive.setId(5l);
        orderFive.setState(OrderState.DONE);
        orderFive.setCreated(cal.getTime());
        
        OrderDTO orderSix = new OrderDTO();
        orderSix.setId(6l);
        orderSix.setState(OrderState.SHIPPED);
        orderSix.setCreated(cal.getTime());
        
        return Arrays.asList(orderOne, orderTwo, orderThree, orderFour, orderFive, orderSix);
    }
}
