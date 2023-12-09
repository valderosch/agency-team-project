package com.team.travel.service;

import com.team.travel.entity.Location;

import java.util.List;

public interface LocationService {
    Location get(Long id);
    List<Location> getCitiesByCountryId(Long countryId);
    Location save(Location location);
    void delete(Long id);
}
