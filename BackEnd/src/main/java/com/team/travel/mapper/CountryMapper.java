package com.team.travel.mapper;

import com.team.travel.dto.country.CountryResponse;
import com.team.travel.dto.country.CountrySaveRequest;
import com.team.travel.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryResponse toResponse(Country entity) {
        return new CountryResponse(entity.getId(), entity.getName());
    }

    public void updateEntity(CountrySaveRequest request, Country entity) {
        entity.setName(request.getName());
    }
}
