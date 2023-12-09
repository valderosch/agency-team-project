package com.team.travel.service;

import com.team.travel.entity.TourCategory;

import java.util.List;
public interface TourCategoryService {
    List<TourCategory> getAll();
    TourCategory getById(Long id);
    TourCategory create(TourCategory category);
    TourCategory update(TourCategory category);
    void delete(Long id);
}
