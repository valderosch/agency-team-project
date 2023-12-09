package com.team.travel.controller;

import com.team.travel.dto.order.OrderCreateRequest;
import com.team.travel.dto.order.OrderResponse;
import com.team.travel.dto.order.OrderUpdateRequest;
import com.team.travel.dto.order.PageOrderResponse;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.dto.order.*;
import com.team.travel.entity.Order;
import com.team.travel.entity.OrderStatus;
import com.team.travel.entity.Role;
import com.team.travel.mapper.OrderMapper;
import com.team.travel.security.UserDetailsImpl;
import com.team.travel.service.OrderService;
import com.team.travel.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"${application.cors.origin}"})
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    public OrderController(OrderService service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<OrderResponse> orders = service.getAll().stream().map(mapper::toResponse).toList();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        Order order = service.get(id);

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole() != Role.ADMIN && !order.getUser().getId().equals(principal.getId())) {
            throw new DatabaseFetchException("Couldn't find Order entity with id " + id);
        }

        return new ResponseEntity<>(mapper.toResponse(order), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("authentication.principal.id == #request.userId")
    public ResponseEntity<OrderResponse> create(@RequestBody OrderCreateRequest request) {
        Order order = service.create(mapper.toEntity(request));
        return new ResponseEntity<>(mapper.toResponse(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderUpdateRequest request) {
        Order order = service.get(id);
        mapper.updateEntity(request, order);
        order = service.update(order);
        return new ResponseEntity<>(mapper.toResponse(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Order order = service.get(id);

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getId().equals(order.getUser().getId()) || order.getStatus() != OrderStatus.PENDING) {
            throw new AccessDeniedException("You are forbidden to delete this order");
        }

        service.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrdersByUserResponse>> getOrdersByUser(@PathVariable Long userId) {
//        List<OrdersByUserResponse> orders =  service.getOrdersByUserId(userId).stream().map(mapper::toOrdersByUserResponse).toList();
//        if (orders.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PageOrderResponse>> getOrdersByUser(
            @PathVariable Long userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {


        return new ResponseEntity<>(service.getAllOrdersPageByUserId(userId,pageNo, pageSize, sortBy, sortDir),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<PageOrderResponse>> getAllPageOrders(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return new ResponseEntity<>(service.getAllOrdersPage(pageNo, pageSize, sortBy, sortDir),HttpStatus.OK);
    }

}
