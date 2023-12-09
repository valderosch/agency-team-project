package com.team.travel.service;

import com.team.travel.entity.User;

import java.util.List;


public interface UserService {
    List<User> getAll();
    User get(Long id);
    User getByEmail(String email);
    User create(User user);
    User update(User user);
    void delete(User user);
}
