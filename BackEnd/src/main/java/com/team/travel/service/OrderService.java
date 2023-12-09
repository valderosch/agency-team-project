package com.team.travel.service;

import com.team.travel.dto.order.PageOrderResponse;
import com.team.travel.entity.Order;

import java.util.List;

public interface OrderService {
    Order get(Long id);

    List<PageOrderResponse> getAllOrdersPage(int pageNo, int pageSize, String sortBy, String sortDir);
    List<Order> getAll();
    Order create(Order order);
    Order update(Order order);
    void delete(Order order);

    List<PageOrderResponse> getAllOrdersPageByUserId(Long userId,int pageNo, int pageSize, String sortBy, String sortDir);
}
