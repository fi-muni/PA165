package cz.fi.muni.pa165.utils;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.facade.OrderFacade;
import cz.fi.muni.pa165.rest.InvalidParameterException;
import cz.fi.muni.pa165.rest.ResourceNotFoundException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
public class OrdersController {
    
    @Inject
    private OrderFacade orderFacade; 

    @RequestMapping( method = RequestMethod.GET, produces = "application/json")
    public final List<OrderDTO> getOrders(@RequestParam("status") String status, 
            @RequestParam(value = "last_week", required = false, defaultValue = "false") boolean lastWeek) {
        if (status.equalsIgnoreCase("ALL") ){
            return orderFacade.getAllOrders();       
        }else if(lastWeek ){
            return orderFacade.getAllOrdersLastWeek(OrderState.valueOf(status));
        }else{
        	//TODO this
        	return orderFacade.getAllOrders();        
//            return orderFacade.getOrdersByState(OrderState.valueOf(status));
        }    
    }
    
    
    @RequestMapping( value="by_user_id/{user_id}", method = RequestMethod.GET, produces = "application/json")
    public final List<OrderDTO> getOrdersByUserId(@PathVariable("user_id") long userId) {
        return orderFacade.getOrdersByUser(userId);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public final OrderDTO getOrder(@PathVariable("id") long id) throws Exception {
        OrderDTO orderDTO = orderFacade.getOrderById(id);                
        if (orderDTO != null){
            return orderDTO;
        }else{
            throw new ResourceNotFoundException();
        }
            
    }
    
    @RequestMapping( value="{order_id}", method = RequestMethod.POST, produces = "application/json")
    public final OrderDTO shipOrder(@PathVariable("order_id") long orderId, @RequestParam("action") String action ) {
        
        if (action.equalsIgnoreCase("CANCEL")){
            orderFacade.cancelOrder(orderId);    
        }else if (action.equalsIgnoreCase("SHIP")){
            orderFacade.shipOrder(orderId);
        }else if (action.equalsIgnoreCase("FINISH")){
            orderFacade.finishOrder(orderId);
        }else{
            throw new InvalidParameterException();
        }
        
        return orderFacade.getOrderById(orderId);
    }
    
    
}

