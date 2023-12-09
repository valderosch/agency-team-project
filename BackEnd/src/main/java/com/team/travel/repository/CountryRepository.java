package com.team.travel.repository;

import com.team.travel.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT c.id FROM Country c WHERE c.name = ?1")
    Long findIdByName(String name);
}
