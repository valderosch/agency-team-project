package com.team.travel.service.impl;

import com.team.travel.entity.Location;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DeleteException;
import com.team.travel.repository.LocationRepository;
import com.team.travel.service.LocationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Location get(Long id) {
        return repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find Location entity with id " + id));
    }

    @Override
    public List<Location> getCitiesByCountryId(Long countryId) {
        return repository.findByCountryId(countryId);
    }

    @Override
    public Location save(Location location) {
        return repository.save(location);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DatabaseFetchException("Could not find Location entity with id " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteException("Could not delete Location entity with id " + id + " because it is referenced in Tour or User");
        }
    }


}
