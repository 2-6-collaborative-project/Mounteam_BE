package com.example.mountain.domain.user.repository;

import com.example.mountain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
