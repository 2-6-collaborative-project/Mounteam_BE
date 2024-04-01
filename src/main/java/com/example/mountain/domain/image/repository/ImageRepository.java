package com.example.mountain.domain.image.repository;

import com.example.mountain.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByFeedId(Long feedId);

    List<Image> deleteByFeedId (Long feedId);

    List<Image> deleteByReviewId (Long reviewId);
}
