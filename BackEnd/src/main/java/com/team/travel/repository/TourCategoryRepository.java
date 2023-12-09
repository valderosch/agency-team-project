package com.team.travel.repository;

import com.team.travel.entity.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
    boolean existsByName(String name);

    @Query("SELECT c.id FROM TourCategory c WHERE c.name = ?1")
    Long findIdByName(String name);
}
