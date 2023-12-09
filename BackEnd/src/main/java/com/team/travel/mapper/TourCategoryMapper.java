package com.team.travel.mapper;

import com.team.travel.dto.category.TourCategoryResponse;
import com.team.travel.dto.category.TourCategorySaveRequest;
import com.team.travel.entity.TourCategory;

import org.springframework.stereotype.Component;
@Component
public class TourCategoryMapper {
    public TourCategoryResponse toResponse(TourCategory entity) {
        return new TourCategoryResponse(entity.getId(), entity.getName());
    }

    public void updateEntity(TourCategorySaveRequest request, TourCategory entity) {
        entity.setName(request.getName());
    }
}
