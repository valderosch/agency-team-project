package com.team.travel.mapper;

import com.team.travel.dto.user.UserResponse;
import com.team.travel.entity.Role;
import com.team.travel.entity.User;
import com.team.travel.dto.user.OrderingUserResponse;
import com.team.travel.dto.user.UserSaveRequest;
import com.team.travel.service.LocationService;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    private final LocationMapper locationMapper;
    private final LocationService locationService;

    public UserMapper(LocationMapper locationMapper, LocationService locationService) {
        this.locationMapper = locationMapper;
        this.locationService = locationService;

    }

    public UserResponse toResponse(User entity){
        UserResponse response = new UserResponse();

        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setEmail(entity.getEmail());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setLocation(locationMapper.toResponse(entity.getLocation()));
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());

        return response;

    }

    public OrderingUserResponse toOrderResponse(User entity) {
        OrderingUserResponse response = new OrderingUserResponse();

        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setEmail(entity.getEmail());
        response.setPhoneNumber(entity.getPhoneNumber());

        return response;

    }

    public void updateEntity(UserSaveRequest request,User entity){

        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setPassword(request.getPassword());
        entity.setLocation(locationService.get(request.getLocationId()));
        entity.setRole(Role.CUSTOMER);
    }

    public void putUpdateEntity(UserSaveRequest request,User entity) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setLocation(locationService.get(request.getLocationId()));
        entity.setRole(Role.CUSTOMER);
    }
}
