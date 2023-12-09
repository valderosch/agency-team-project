package com.team.travel.controller;

import com.team.travel.dto.location.LocationSaveRequest;
import com.team.travel.dto.location.CityResponse;
import com.team.travel.dto.location.LocationResponse;
import com.team.travel.entity.Location;
import com.team.travel.mapper.LocationMapper;
import com.team.travel.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@CrossOrigin(origins = {"${application.cors.origin}"})
@RequestMapping(value = "/api/locations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {
    private final LocationService service;
    private final LocationMapper mapper;

    public LocationController(LocationService service, LocationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> get(@PathVariable Long id) {
        LocationResponse response = mapper.toResponse(service.get(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<CityResponse>> getCitiesByCountry(@PathVariable Long countryId) {
        List<CityResponse> cities = service.getCitiesByCountryId(countryId).stream().map(mapper::toCityResponse).toList();
        if (cities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationResponse> create(@RequestBody LocationSaveRequest request) {
        Location location = new Location();
        mapper.updateEntity(request, location);
        location = service.save(location);
        return new ResponseEntity<>(mapper.toResponse(location), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationResponse> update(@PathVariable Long id, @RequestBody LocationSaveRequest request) {
        Location location = service.get(id);
        mapper.updateEntity(request, location);
        location = service.save(location);
        return new ResponseEntity<>(mapper.toResponse(location), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
