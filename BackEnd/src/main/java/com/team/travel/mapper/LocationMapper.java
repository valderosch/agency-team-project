package com.team.travel.mapper;

import com.team.travel.dto.location.CityResponse;
import com.team.travel.dto.location.LocationResponse;
import com.team.travel.dto.location.LocationSaveRequest;
import com.team.travel.entity.Location;
import com.team.travel.service.CountryService;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    private final CountryMapper countryMapper;
    private final CountryService countryService;

    public LocationMapper(CountryMapper countryMapper, CountryService countryService) {
        this.countryMapper = countryMapper;
        this.countryService = countryService;
    }

    public LocationResponse toResponse(Location entity) {
        LocationResponse response = new LocationResponse();

        response.setId(entity.getId());
        response.setCity(entity.getCity());
        response.setCountry(countryMapper.toResponse(entity.getCountry()));

        return response;
    }

    public CityResponse toCityResponse(Location entity) {
        CityResponse response = new CityResponse();

        response.setId(entity.getId());
        response.setName(entity.getCity());

        return response;
    }

    public void updateEntity(LocationSaveRequest request, Location entity) {
        entity.setCity(request.getCity());
        entity.setCountry(countryService.get(request.getCountryId()));
    }
}
