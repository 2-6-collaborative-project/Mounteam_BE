package com.example.mountain.domain.user.repository;

import com.example.mountain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findById(Long id);
}
