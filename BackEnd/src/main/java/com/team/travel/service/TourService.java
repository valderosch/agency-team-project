package com.team.travel.service;

import com.team.travel.entity.Location;
import com.team.travel.entity.Tour;
import com.team.travel.entity.TourStatus;
import com.team.travel.dto.tour.PageTourResponse;

import java.time.LocalDate;
import java.util.List;

public interface TourService {

    List<Tour> getAll();

     List<PageTourResponse> getAllToursPage(int pageNo,
                                            int pageSize,
                                            String sortBy,
                                            String sortDir,
                                            TourStatus status,
                                            int priceStart,
                                            int priceEnd,
                                            Location departure,
                                            Location destination,
                                            LocalDate startDate,
                                            LocalDate endDate);
    List<Tour> getAllAvailable();

    Tour get(Long id);
    Tour save(Tour tour);
    void delete(Long id);
    String getImagePathById(Long id);

    List<Tour> findToursByFilter(TourStatus status,int priceStart, int priceEnd);


}
