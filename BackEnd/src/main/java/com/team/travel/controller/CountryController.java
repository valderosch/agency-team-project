package com.team.travel.controller;

import com.team.travel.dto.country.CountryResponse;
import com.team.travel.dto.country.CountrySaveRequest;
import com.team.travel.entity.Country;
import com.team.travel.mapper.CountryMapper;
import com.team.travel.service.CountryService;
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
@RequestMapping(value = "/api/countries", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {
    private final CountryService service;
    private final CountryMapper mapper;

    public CountryController(CountryService service, CountryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<CountryResponse>> getAll() {
        List<CountryResponse> countries = service.getAll().stream().map(mapper::toResponse).toList();
        if (countries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> get(@PathVariable Long id) {
        CountryResponse response = mapper.toResponse(service.get(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CountryResponse> create(@RequestBody CountrySaveRequest request) {
        Country country = new Country();
        mapper.updateEntity(request, country);
        country = service.create(country);
        return new ResponseEntity<>(mapper.toResponse(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CountryResponse> update(@PathVariable Long id, @RequestBody CountrySaveRequest request) {
        Country country = service.get(id);
        mapper.updateEntity(request, country);
        country = service.update(country);
        return new ResponseEntity<>(mapper.toResponse(country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
