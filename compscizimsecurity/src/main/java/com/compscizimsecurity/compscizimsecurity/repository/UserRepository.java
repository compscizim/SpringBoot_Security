package com.compscizimsecurity.compscizimsecurity.repository;

import com.compscizimsecurity.compscizimsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username);
}
