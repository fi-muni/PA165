package cz.muni.fi.pa165.mvc.controllers;

import cz.fi.muni.pa165.dto.OrderDTO;
import cz.fi.muni.pa165.dto.OrderItemDTO;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.enums.OrderState;
import cz.fi.muni.pa165.exceptions.EshopServiceException;
import cz.fi.muni.pa165.facade.OrderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringMVC Controller for handling orders.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private MessageSource messageSource;

    /**
     * Shows a list of orders, filtered by specified filter
     *
     * @param filter selects which orders should be displayed
     * @param model  data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list/{filter}", method = RequestMethod.GET)
    public String list(@PathVariable String filter, Model model) {
        List<OrderDTO> orders;
        switch (filter) {
            case "all":
                orders = orderFacade.getAllOrders();
                break;
            case "received":
                orders = orderFacade.getOrdersByState(OrderState.RECEIVED);
                break;
            case "shipped":
                orders = orderFacade.getOrdersByState(OrderState.SHIPPED);
                break;
            case "canceled":
                orders = orderFacade.getOrdersByState(OrderState.CANCELED);
                break;
            case "done":
                orders = orderFacade.getOrdersByState(OrderState.DONE);
                break;
            case "unprocessed":
                orders = new ArrayList<>();
                orders.addAll(orderFacade.getOrdersByState(OrderState.RECEIVED));
                orders.addAll(orderFacade.getOrdersByState(OrderState.SHIPPED));
                break;
            default:
                orders = new ArrayList<>();
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String unprocessed(@PathVariable long id, Model model) {
        OrderDTO order = orderFacade.getOrderById(id);
        BigDecimal totalPrice = BigDecimal.ZERO;
        Currency totalCurrency = null;
        for (OrderItemDTO item : order.getOrderItems()) {
            totalPrice = totalPrice.add(item.getPricePerItem().getValue().multiply(new BigDecimal(item.getAmount())));
            Currency itemCurrency = item.getPricePerItem().getCurrency();
            if(totalCurrency==null) {totalCurrency=itemCurrency;}
            if(!totalCurrency.equals(itemCurrency)) {
                model.addAttribute("alert_danger","Cannot sum prices in different currencies ");
                break;
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCurrency", totalCurrency);
        model.addAttribute("order", order);
        return "order/detail";
    }

    @RequestMapping(value = "/ship/{id}", method = RequestMethod.POST)
    public String ship(@PathVariable long id, Model model,UriComponentsBuilder uriBuilder,RedirectAttributes redirectAttributes) {
        try {
            orderFacade.shipOrder(id);
            redirectAttributes.addFlashAttribute("alert_success", "Order number "+id+" was shipped.");
        } catch (EshopServiceException ex) {
            log.warn("cannot ship order {}",id);
            redirectAttributes.addFlashAttribute("alert_danger", "Order number "+id+" was not shipped. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/order/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/finish/{id}", method = RequestMethod.POST)
    public String finish(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        try {
            orderFacade.finishOrder(id);
            redirectAttributes.addFlashAttribute("alert_success", "Order number "+id+" was finished.");
        } catch (EshopServiceException ex) {
            log.warn("cannot finish order {}",id);
            redirectAttributes.addFlashAttribute("alert_danger", "Order number "+id+" was not finished. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/order/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.POST)
    public String cancel(@PathVariable long id, Model model,UriComponentsBuilder uriBuilder,RedirectAttributes redirectAttributes) {
        try {
            orderFacade.cancelOrder(id);
            redirectAttributes.addFlashAttribute("alert_success", "Order number "+id+" was canceled.");
        } catch (EshopServiceException ex) {
            log.warn("cannot cancel order {}",id);
            redirectAttributes.addFlashAttribute("alert_danger", "Order number "+id+" was not canceled. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/order/detail/{id}").buildAndExpand(id).encode().toUriString();
    }



}
