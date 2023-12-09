package com.team.travel.service.impl;

import com.team.travel.dto.tour.PageTourResponse;
import com.team.travel.entity.Location;
import com.team.travel.entity.Tour;
import com.team.travel.entity.TourStatus;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DeleteException;
import com.team.travel.mapper.TourMapper;
import com.team.travel.repository.TourRepository;
import com.team.travel.service.LocationService;
import com.team.travel.service.TourService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Service
public class TourServiceImpl implements TourService {
    private final TourRepository repository;

    private final TourMapper mapper;

    private final LocationService locationService;

    public TourServiceImpl(TourRepository repository, TourMapper mapper,LocationService locationService) {
        this.repository = repository;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @Override
    public List<Tour> getAll() {
        return repository.findAll();
    }



    public List<PageTourResponse> getAllToursPage(int pageNo,
                                                  int pageSize,
                                                  String sortBy,
                                                  String sortDir,
                                                  TourStatus status,
                                                  int priceStart,
                                                  int priceEnd,
                                                  Location departure,
                                                  Location destination,
                                                  LocalDate startDate,
                                                  LocalDate endDate) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Specification<Tour> specification = (root,query,criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            }

                predicates.add(criteriaBuilder.greaterThan(root.get("price"),priceStart));

                predicates.add(criteriaBuilder.lessThan(root.get("price"),priceEnd));

                if(departure != null) {
                    predicates.add(criteriaBuilder.equal(root.get("departure"), departure));
                }
                if(destination != null) {
                    predicates.add(criteriaBuilder.equal(root.get("destination"), destination));
                }
                if(startDate != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("startDate"), startDate));
                }
                if(endDate != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("endDate"), endDate));
                }




            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };



        Page<Tour> tours = repository.findAll(specification,pageable);

        List<Tour> listOfTours = tours.getContent();

        List<PageTourResponse> responseList = new ArrayList<>();

        for (Tour tour : listOfTours) {
            PageTourResponse pageTourResponse = new PageTourResponse();
            pageTourResponse.setContent(Collections.singletonList(mapper.toResponse(tour)));
            pageTourResponse.setPageNo(tours.getNumber());
            pageTourResponse.setPageSize(tours.getSize());
            pageTourResponse.setTotalElements(tours.getTotalElements());
            pageTourResponse.setTotalPages(tours.getTotalPages());
            pageTourResponse.setLast(tours.isLast());

            responseList.add(pageTourResponse);
        }

        return responseList;
    }
    @Override
    public List<Tour> getAllAvailable() {
        return repository.findAllAvailable();
    }



    @Override
    public Tour get(Long id) {
        return repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find Tour entity with id " + id));
    }

    @Override
    public Tour save(Tour tour) {
        return repository.save(tour);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DatabaseFetchException("Could not find Tour entity with id " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteException("Could not delete Tour entity with id " + id + " because it is already ordered");
        }
    }




    @Override
    public String getImagePathById(Long id) {
        return repository.findImagePathById(id);
    }

    /**
     * @param priceStart
     * @return
     */
    @Override
    public List<Tour> findToursByFilter(TourStatus status, int priceStart, int priceEnd) {
       return repository.findToursByFilter(status,priceStart,priceEnd);
    }


}
