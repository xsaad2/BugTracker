package com.bugtracker.back.repositories;

import com.bugtracker.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findEmployeeById(Long id);

    void deleteUserById(Long id);

    Optional<UserDetails> findByEmail(String email);
}
