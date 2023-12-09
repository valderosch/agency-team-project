package com.team.travel.dto.order;

import com.team.travel.entity.OrderStatus;

public class OrderUpdateRequest {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderUpdateRequest{" +
                "status=" + status +
                '}';
    }
}
