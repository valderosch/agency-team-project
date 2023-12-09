package com.team.travel.dto.order;

import com.team.travel.dto.tour.TourPreviewResponse;
import com.team.travel.entity.OrderStatus;

import java.math.BigDecimal;

public class OrdersByUserResponse {

    private Long id;
    private TourPreviewResponse tour;
    private OrderStatus status;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourPreviewResponse getTour() {
        return tour;
    }

    public void setTour(TourPreviewResponse tour) {
        this.tour = tour;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderByUserResponse{" +
                "id=" + id +
                ", tour='" + tour + '\'' +
                ", status='" + status + '\'' +
                ", price='" + status + '\'' +
                '}';
    }
}
