package com.team.travel.mapper;

import com.team.travel.dto.order.OrderCreateRequest;
import com.team.travel.dto.order.OrderUpdateRequest;
import com.team.travel.entity.Order;
import com.team.travel.entity.OrderStatus;
import com.team.travel.entity.Tour;
import com.team.travel.service.TourService;
import com.team.travel.dto.order.OrderResponse;
import com.team.travel.dto.order.OrdersByUserResponse;
import com.team.travel.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {
    private final UserMapper userMapper;
    private final TourMapper tourMapper;
    private final UserService userService;
    private final TourService tourService;

    public OrderMapper(UserMapper userMapper, TourMapper tourMapper, UserService userService, TourService tourService) {
        this.userMapper = userMapper;
        this.tourMapper = tourMapper;
        this.userService = userService;
        this.tourService = tourService;
    }

    public OrderResponse toResponse(Order entity) {
        OrderResponse response = new OrderResponse();

        response.setId(entity.getId());
        response.setUser(userMapper.toOrderResponse(entity.getUser()));
        response.setTour(tourMapper.toPreviewResponse(entity.getTour()));
        response.setStatus(entity.getStatus());
        response.setPrice(entity.getPrice());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public Order toEntity(OrderCreateRequest request) {
        Order order = new Order();

        order.setUser(userService.get(request.getUserId()));

        Tour tour = tourService.get(request.getTourId());
        order.setTour(tour);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal price = tour.getPrice().subtract(tour.getPrice().multiply(tour.getDiscount()));
        order.setPrice(price);

        return order;
    }

    public void updateEntity(OrderUpdateRequest request, Order entity) {
        entity.setStatus(request.getStatus());
    }

    public OrdersByUserResponse toOrdersByUserResponse(Order entity) {
        OrdersByUserResponse response = new OrdersByUserResponse();

        response.setId(entity.getId());
        response.setTour(tourMapper.toPreviewResponse(entity.getTour()));
        response.setStatus(entity.getStatus());
        response.setPrice(entity.getPrice());

        return response;
    }
}
