package com.team.travel.dto.tour;

import com.team.travel.dto.location.LocationResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourPreviewResponse {
    private Long id;
    private String name;
    private String imagePath;
    private LocationResponse departure;
    private LocationResponse destination;
    private BigDecimal initialPrice;
    private int discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocationResponse getDeparture() {
        return departure;
    }

    public void setDeparture(LocationResponse departure) {
        this.departure = departure;
    }

    public LocationResponse getDestination() {
        return destination;
    }

    public void setDestination(LocationResponse destination) {
        this.destination = destination;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TourPreviewResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", departure=" + departure +
                ", destination=" + destination +
                ", initialPrice=" + initialPrice +
                ", discountPercentage=" + discountPercentage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
