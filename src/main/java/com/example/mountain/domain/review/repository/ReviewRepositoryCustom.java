package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListScrollResponse;
import com.example.mountain.domain.review.dto.response.ReviewMainScrollResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    ReviewListScrollResponse findAllReview(Pageable pageable, Long userId);
    ReviewMainScrollResponse findAllReview(Pageable pageable);

    ReviewListScrollResponse getImagesInReviews (Long userId, Pageable pageable, Long cursorId);
}
