package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.entity.ReviewTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagRepository extends JpaRepository<ReviewTagMap, Long> {
}
