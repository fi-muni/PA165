package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.OrderState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class OrderDTO
{
    private Long id;

    private UserDTO user;

    private List<OrderItemDTO> orderItems = new ArrayList<>();

    private Date created;

    private OrderState state;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems=orderItems;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderDTO other = (OrderDTO) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (state != other.state)
            return false;
        if (user == null) {
            if (other.user != null)

                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
