package com.example.mountain.domain.badge.repository;

import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<Badge> findByUser(User user);
    Long countByUser(User user);
}
