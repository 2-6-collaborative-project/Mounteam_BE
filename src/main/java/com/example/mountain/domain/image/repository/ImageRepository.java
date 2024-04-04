package com.example.mountain.domain.image.repository;

import com.example.mountain.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> deleteByReviewId (Long reviewId);
}
