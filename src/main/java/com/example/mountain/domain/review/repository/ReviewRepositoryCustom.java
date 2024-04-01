package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<ReviewListResponse> findAllReview(Pageable pageable, Long userId);
}
