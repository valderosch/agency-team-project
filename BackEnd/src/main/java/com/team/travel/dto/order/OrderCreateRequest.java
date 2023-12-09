package com.team.travel.dto.order;

public class OrderCreateRequest {
    private Long tourId;
    private Long userId;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderCreateRequest{" +
                "tourId=" + tourId +
                ", userId=" + userId +
                '}';
    }
}
