package com.team.travel.service.impl;

import com.team.travel.entity.TourCategory;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DeleteException;
import com.team.travel.exception.DuplicateException;
import com.team.travel.repository.TourCategoryRepository;
import com.team.travel.service.TourCategoryService;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TourCategoryServiceImpl implements TourCategoryService {
    private final TourCategoryRepository repository;

    public TourCategoryServiceImpl(TourCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TourCategory> getAll() {
        return repository.findAll();
    }

    @Override
    public TourCategory getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new DatabaseFetchException("Could not find TourCategory entity with id " + id));
    }

    @Override
    public TourCategory create(TourCategory category) {
        if (repository.existsByName(category.getName())) {
            throw new DuplicateException("Category with name '" + category.getName() + "' is already in the database");
        }
        return repository.save(category);
    }

    @Override
    public TourCategory update(TourCategory category) {
        Long idOfDuplicate = repository.findIdByName(category.getName());
        if (idOfDuplicate != null && !idOfDuplicate.equals(category.getId())) {
            throw new DuplicateException(("TourCategory with name '" + category.getName() + "' is already exists"));
        }
        return repository.save(category);
    }

    @Override
    public void delete(Long id) {
        TourCategory deleteCategory = repository.findById(id).orElse(null);
        if (deleteCategory != null && deleteCategory.getTours().isEmpty()) {
            repository.delete(deleteCategory);
        }
        else {
            throw new DeleteException("Can not delete TourCategory. This TourCategory is not empty");
        }
    }
}
