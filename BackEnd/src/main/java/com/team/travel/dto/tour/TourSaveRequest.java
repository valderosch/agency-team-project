package com.team.travel.dto.tour;

import com.team.travel.entity.TourStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourSaveRequest {
    private Long categoryId;
    private String name;
    private String description;
    private String imagePath;
    private Long departureId;
    private Long destinationId;
    private BigDecimal price;
    private int discountPercentage;
    private Integer numOfPeople;
    private Integer availableOrderCount;
    private TourStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "TourSaveRequest{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", departureId=" + departureId +
                ", destinationId=" + destinationId +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", numOfPeople=" + numOfPeople +
                ", availableOrderCount=" + availableOrderCount +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
