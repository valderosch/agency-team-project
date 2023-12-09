package com.team.travel.repository;

import com.team.travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Maksym Kleiman
 * @project Tour Agency project
 **/

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u.id FROM User u WHERE u.email = ?1 ")
    Long findIdByEmail(String name);
    Optional<User> findByEmail(String email);
}
