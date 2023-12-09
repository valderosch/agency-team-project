package com.team.travel.repository;

import com.team.travel.entity.Tour;
import com.team.travel.entity.TourStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {
    @Query("SELECT t.imagePath FROM Tour t WHERE t.id = ?1")
    String findImagePathById(Long id);
    @Query("SELECT t FROM Tour t WHERE t.status <> 'NOT_AVAILABLE'")
    List<Tour> findAllAvailable();
    @Query("SELECT t FROM Tour t WHERE t.status = ?1 AND t.price BETWEEN ?2 AND ?3")
    List<Tour> findToursByFilter(TourStatus status,int priceStart, int priceEnd);
}
