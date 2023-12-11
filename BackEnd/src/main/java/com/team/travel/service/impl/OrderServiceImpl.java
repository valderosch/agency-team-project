package com.team.travel.service.impl;

import com.team.travel.dto.order.PageOrderResponse;
import com.team.travel.entity.Order;
import com.team.travel.entity.Tour;
import com.team.travel.entity.TourStatus;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.TourUnavailableException;
import com.team.travel.mapper.OrderMapper;
import com.team.travel.repository.OrderRepository;
import com.team.travel.service.OrderService;
import com.team.travel.service.TourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final TourService tourService;

    private final OrderMapper mapper;



    public OrderServiceImpl(OrderRepository repository, TourService tourService, OrderMapper mapper) {
        this.repository = repository;
        this.tourService = tourService;
        this.mapper = mapper;
    }

    @Override
    public Order get(Long id) {
        return repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find Order entity with id " + id));
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public List<PageOrderResponse> getAllOrdersPage(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

       Page<Order> orders = repository.findAll(pageable);

       List<Order> orderList = orders.getContent();

       List<PageOrderResponse> responseList = new ArrayList<>();

        for (Order order : orderList) {
            PageOrderResponse pageOrderResponse = new PageOrderResponse();
            pageOrderResponse.setContent(Collections.singletonList(mapper.toResponse(order)));
            pageOrderResponse.setPageNo(orders.getNumber());
            pageOrderResponse.setPageSize(orders.getSize());
            pageOrderResponse.setTotalElements(orders.getTotalElements());
            pageOrderResponse.setTotalPages(orders.getTotalPages());
            pageOrderResponse.setLast(orders.isLast());

            responseList.add(pageOrderResponse);
        }

        return responseList;
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public Order create(Order order) {
        Tour tour = order.getTour();
        if (tour.getStatus() == TourStatus.NOT_AVAILABLE) {
            throw new TourUnavailableException("Tour with id " + tour.getId() + " is not available for ordering");
        }

        Integer tourOrderCount = tour.getAvailableOrderCount();
        if (tourOrderCount == 0) {
            throw new TourUnavailableException("Tour with id " + tour.getId() + " is fully booked");
        }

        tour.setAvailableOrderCount(tourOrderCount - 1);
        tourService.save(tour);

        return repository.save(order);
    }

    @Override
    public Order update(Order order) {
        return repository.save(order);
    }

    @Override
    public void delete(Order order) {
        repository.delete(order);
    }

    /**
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public List<PageOrderResponse> getAllOrdersPageByUserId(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Order> orders = repository.findByUserId(userId,pageable);

        List<Order> orderList = orders.getContent();

        List<PageOrderResponse> responseList = new ArrayList<>();

        for (Order order : orderList) {
            PageOrderResponse pageOrderResponse = new PageOrderResponse();
            pageOrderResponse.setContent(Collections.singletonList(mapper.toResponse(order)));
            pageOrderResponse.setPageNo(orders.getNumber());
            pageOrderResponse.setPageSize(orders.getSize());
            pageOrderResponse.setTotalElements(orders.getTotalElements());
            pageOrderResponse.setTotalPages(orders.getTotalPages());
            pageOrderResponse.setLast(orders.isLast());

            responseList.add(pageOrderResponse);
        }

        return responseList;
    }
}
