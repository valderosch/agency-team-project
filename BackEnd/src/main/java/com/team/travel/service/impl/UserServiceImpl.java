package com.team.travel.service.impl;

import com.team.travel.entity.User;
import com.team.travel.exception.DatabaseFetchException;
import com.team.travel.exception.DeleteException;
import com.team.travel.exception.DuplicateException;
import com.team.travel.repository.UserRepository;
import com.team.travel.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User get(Long id) {
        return repository.findById(id).orElseThrow(()-> new DatabaseFetchException("Could not find User entity with id: " + id));
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with email " + email));
    }

    @Override
    public User create(User user) {
        if (repository.findIdByEmail(user.getEmail()) != null) {
            throw new DuplicateException("акаунт з ел. поштою '" + user.getEmail() + "' вже існує");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        Long emailId = repository.findIdByEmail(user.getEmail());
        if (emailId != null && !emailId.equals(user.getId())) {
            throw new DuplicateException("акаунт з ел. поштою '" + user.getEmail() + "' вже існує");
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        try {
            repository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteException("Could not delete User entity with id " + user.getId() + " because it is referenced in Token or Order");
        }
    }
}
