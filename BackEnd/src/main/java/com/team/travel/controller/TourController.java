package com.team.travel.controller;

import com.team.travel.entity.Role;
import com.team.travel.entity.Tour;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.mapper.TourMapper;
import com.team.travel.utils.AppConstants;
import com.team.travel.dto.tour.PageTourResponse;
import com.team.travel.dto.tour.TourResponse;
import com.team.travel.dto.tour.TourSaveRequest;
import com.team.travel.entity.Location;
import com.team.travel.entity.TourStatus;
import com.team.travel.security.UserDetailsImpl;
import com.team.travel.service.ImageService;
import com.team.travel.service.LocationService;
import com.team.travel.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"${application.cors.origin}"})
@RequestMapping(value = "/api/tours", produces = MediaType.APPLICATION_JSON_VALUE)
public class TourController {
    private final TourService service;
    private final TourMapper mapper;
    private final ImageService imageService;

    private final LocationService locationService;



    public TourController(TourService service, TourMapper mapper, ImageService imageService, LocationService locationService) {
        this.service = service;
        this.mapper = mapper;
        this.imageService = imageService;
        this.locationService = locationService;

    }

    @GetMapping("/")
    public ResponseEntity<List<TourResponse>> getAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TourResponse> tours;

        if (principal.equals("anonymousUser") || ((UserDetailsImpl) principal).getRole() == Role.CUSTOMER) {
            tours = service.getAllAvailable().stream().map(mapper::toResponse).toList();
        } else {

            tours = service.getAll().stream().map(mapper::toResponse).toList();

        }

        if (tours.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tours, HttpStatus.OK);
    }




@GetMapping()
public ResponseEntity<List<PageTourResponse>> getAllPageTours(
        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
        @RequestParam(value = "status", defaultValue = "AVAILABLE", required = false) TourStatus status,
        @RequestParam(value = "startPrice", defaultValue = "0", required = false) int priceStart,
        @RequestParam(value = "endPrice", defaultValue = "999999", required = false) int priceEnd,
        @RequestParam(value = "departure",required = false) Long departureId,
        @RequestParam(value = "destination",required = false) Long destinationId,
        @RequestParam(value = "startDate",required = false) String startDateStr,
        @RequestParam(value = "endDate",required = false) String endDateStr

) {
        Location departure = null;

        if(departureId != null && departureId !=0) {
            departure  = locationService.get(departureId);
        }

        Location destination = null;
        if(destinationId != null && destinationId !=0) {
            destination = locationService.get(destinationId);
        }

        LocalDate startDate = null;
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        LocalDate endDate = null;
        if (endDateStr != null && !endDateStr.isEmpty()) {
            startDate = LocalDate.parse(endDateStr);
        }

    return new ResponseEntity<>(service.getAllToursPage(pageNo, pageSize, sortBy, sortDir,
            status,priceStart,priceEnd,departure,destination,startDate,endDate),HttpStatus.OK);
}



    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id) {
        Tour tour = service.get(id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean notAdmin = principal.equals("anonymousUser") || ((UserDetailsImpl) principal).getRole() != Role.ADMIN;
        if (notAdmin && tour.getStatus() == TourStatus.NOT_AVAILABLE) {
            throw new DatabaseFetchException("Couldn't find Tour entity with id " + id);
        }

        return new ResponseEntity<>(mapper.toResponse(tour), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourResponse> create(@RequestBody TourSaveRequest request) {
        Tour tour = new Tour();
        mapper.updateEntity(request, tour);
        tour = service.save(tour);
        return new ResponseEntity<>(mapper.toResponse(tour), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourResponse> update(@PathVariable Long id, @RequestBody TourSaveRequest request) {
        Tour tour = service.get(id);
        mapper.updateEntity(request, tour);
        tour = service.save(tour);
        return new ResponseEntity<>(mapper.toResponse(tour), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        String path = service.getImagePathById(id);
        service.delete(id);
        imageService.delete(path);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/filter/")
//    public ResponseEntity<List<TourResponse>> getToursByFilter(
//
//            @RequestParam(value = "status", defaultValue = "AVAILABLE", required = false) TourStatus status,
//            @RequestParam(value = "startPrice", defaultValue = "0", required = false) int priceStart,
//            @RequestParam(value = "endPrice", defaultValue = "999999", required = false) int priceEnd
//            ) {
//        List<TourResponse> tours = service.findToursByFilter(status,priceStart,priceEnd).stream().map(mapper::toResponse).toList();
//        if(tours.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(tours,HttpStatus.OK);
//    }








}
