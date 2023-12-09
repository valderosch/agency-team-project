package com.team.travel.dto.tour;

import com.team.travel.dto.category.TourCategoryResponse;
import com.team.travel.dto.location.LocationResponse;
import com.team.travel.entity.TourStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TourResponse {
    private Long id;
    private TourCategoryResponse category;
    private String name;
    private String description;
    private String imagePath;
    private LocationResponse departure;
    private LocationResponse destination;
    private BigDecimal initialPrice;
    private int discountPercentage;
    private Integer numOfPeople;
    private Integer availableOrderCount; // FOR ORDINARY USER THIS FIELD IS ABSENT / NULL
    private TourStatus status; // FOR ORDINARY USER STATUS CAN BE ONLY HOT OR AVAILABLE
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourCategoryResponse getCategory() {
        return category;
    }

    public void setCategory(TourCategoryResponse category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(Integer numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public Integer getAvailableOrderCount() {
        return availableOrderCount;
    }

    public void setAvailableOrderCount(Integer availableOrderCount) {
        this.availableOrderCount = availableOrderCount;
    }

    public TourStatus getStatus() {
        return status;
    }

    public void setStatus(TourStatus status) {
        this.status = status;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TourResponse{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", departure=" + departure +
                ", destination=" + destination +
                ", initialPrice=" + initialPrice +
                ", discountPercentage=" + discountPercentage +
                ", numOfPeople=" + numOfPeople +
                ", availableOrderCount=" + availableOrderCount +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
