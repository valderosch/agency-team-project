package com.team.travel.service.impl;

import com.team.travel.entity.Country;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DeleteException;
import com.team.travel.exception.DuplicateException;
import com.team.travel.repository.CountryRepository;
import com.team.travel.service.CountryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    public CountryServiceImpl(CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Country> getAll() {
        return repository.findAll();
    }

    @Override
    public Country get(Long id) {
        return repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find Country entity with id " + id));
    }

    @Override
    public Country create(Country country) {
        if (repository.findIdByName(country.getName()) != null) {
            throw new DuplicateException("Country with name '" + country.getName() + "' is already in the database");
        }
        return repository.save(country);
    }

    @Override
    public Country update(Country country) {
        Long idOfDuplicate = repository.findIdByName(country.getName());
        if (idOfDuplicate != null && !idOfDuplicate.equals(country.getId())) {
            throw new DuplicateException("Country with name '" + country.getName() + "' is already in the database");
        }
        return repository.save(country);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DatabaseFetchException("Could not find Country entity with id " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteException("Could not delete Country entity with id " + id + " because it is referenced in Location");
        }
    }
}
