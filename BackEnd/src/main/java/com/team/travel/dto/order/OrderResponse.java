package com.team.travel.dto.order;

import com.team.travel.dto.tour.TourPreviewResponse;
import com.team.travel.entity.OrderStatus;
import com.team.travel.dto.user.OrderingUserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private OrderingUserResponse user;
    private TourPreviewResponse tour;
    private OrderStatus status;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderingUserResponse getUser() {
        return user;
    }

    public void setUser(OrderingUserResponse user) {
        this.user = user;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", user=" + user +
                ", tour=" + tour +
                ", status=" + status +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
