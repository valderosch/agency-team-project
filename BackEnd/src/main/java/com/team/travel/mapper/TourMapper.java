package com.team.travel.mapper;

import com.team.travel.entity.Tour;
import com.team.travel.entity.TourCategory;
import com.team.travel.dto.tour.TourPreviewResponse;
import com.team.travel.dto.tour.TourResponse;
import com.team.travel.dto.tour.TourSaveRequest;
import com.team.travel.entity.Location;
import com.team.travel.service.LocationService;
import com.team.travel.service.TourCategoryService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TourMapper {
    private final TourCategoryMapper tourCategoryMapper;
    private final LocationMapper locationMapper;
    private final TourCategoryService tourCategoryService;
    private final LocationService locationService;

    public TourMapper(TourCategoryMapper tourCategoryMapper, LocationMapper locationMapper, TourCategoryService tourCategoryService, LocationService locationService) {
        this.tourCategoryMapper = tourCategoryMapper;
        this.locationMapper = locationMapper;
        this.tourCategoryService = tourCategoryService;
        this.locationService = locationService;
    }

    public TourResponse toResponse(Tour entity) {
        TourResponse response = new TourResponse();

        response.setId(entity.getId());
        response.setCategory(tourCategoryMapper.toResponse(entity.getCategory()));
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setImagePath(entity.getImagePath());
        response.setDeparture(locationMapper.toResponse(entity.getDeparture()));
        response.setDestination(locationMapper.toResponse(entity.getDestination()));
        response.setInitialPrice(entity.getPrice());
        response.setDiscountPercentage(entity.getDiscount().multiply(BigDecimal.valueOf(100)).intValue());
        response.setNumOfPeople(entity.getNumOfPeople());
        response.setAvailableOrderCount(entity.getAvailableOrderCount());
        response.setStatus(entity.getStatus());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }

    public TourPreviewResponse toPreviewResponse(Tour entity) {
        TourPreviewResponse response = new TourPreviewResponse();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImagePath(entity.getImagePath());
        response.setDeparture(locationMapper.toResponse(entity.getDeparture()));
        response.setDestination(locationMapper.toResponse(entity.getDestination()));
        response.setInitialPrice(entity.getPrice());
        response.setDiscountPercentage(entity.getDiscount().multiply(BigDecimal.valueOf(100)).intValue());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());

        return response;
    }

    public void updateEntity(TourSaveRequest request, Tour entity) {
        TourCategory category = tourCategoryService.getById(request.getCategoryId());
        entity.setCategory(category);

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setImagePath(request.getImagePath());

        Location location = locationService.get(request.getDepartureId());
        entity.setDeparture(location);
        location = locationService.get(request.getDestinationId());
        entity.setDestination(location);

        entity.setPrice(request.getPrice());

        BigDecimal discount = BigDecimal.valueOf(request.getDiscountPercentage() / 100d);
        entity.setDiscount(discount);

        entity.setNumOfPeople(request.getNumOfPeople());
        entity.setAvailableOrderCount(request.getAvailableOrderCount());
        entity.setStatus(request.getStatus());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
    }
}
