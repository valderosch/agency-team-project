package com.team.travel.controller;

import com.team.travel.dto.user.UserResponse;
import com.team.travel.entity.User;
import com.team.travel.dto.user.UserSaveRequest;
import com.team.travel.mapper.UserMapper;
import com.team.travel.security.UserDetailsImpl;
import com.team.travel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@CrossOrigin(origins={"${application.cors.origin}"})
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> users = service.getAll().stream().map(mapper::toResponse).toList();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        UserResponse user = mapper.toResponse(service.get(id));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@RequestBody UserSaveRequest request){
        User user = new User();
        mapper.updateEntity(request,user);
        user = service.create(user);

        return new ResponseEntity<>(mapper.toResponse(user),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #id")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserSaveRequest request){
        User user = service.get(id);
        mapper.putUpdateEntity(request,user);
        user = service.update(user);
        return new ResponseEntity<>(mapper.toResponse(user),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        User user = service.get(id);
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!principal.getId().equals(user.getId())) {
            throw new AccessDeniedException("You are forbidden to delete this user");
        }

        service.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
