package com.team.travel.controller;

import com.team.travel.dto.category.TourCategoryResponse;
import com.team.travel.dto.category.TourCategorySaveRequest;
import com.team.travel.entity.TourCategory;
import com.team.travel.service.TourCategoryService;
import com.team.travel.mapper.TourCategoryMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@CrossOrigin(origins = {"${application.cors.origin}"})
@RequestMapping(value = "api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class TourCategoryController {

    private final TourCategoryService service;
    private final TourCategoryMapper mapper;

    public TourCategoryController(TourCategoryService service, TourCategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<TourCategoryResponse>> getAll() {
        List<TourCategoryResponse> categories = service.getAll().stream().map(mapper::toResponse).toList();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourCategoryResponse> getById(@PathVariable Long id) {
        TourCategoryResponse response = mapper.toResponse(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourCategoryResponse> create(@RequestBody TourCategorySaveRequest request) {
        TourCategory category = new TourCategory();
        mapper.updateEntity(request, category);
        category = service.create(category);
        return new ResponseEntity<>(mapper.toResponse(category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourCategoryResponse> update(@PathVariable Long id, @RequestBody TourCategorySaveRequest request) {
        TourCategory category = service.getById((id));
        mapper.updateEntity(request, category);
        category = service.update(category);
        return new ResponseEntity<>(mapper.toResponse(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}