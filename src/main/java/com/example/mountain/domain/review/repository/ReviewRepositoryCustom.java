package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {
    Slice<ReviewListResponse> findAllReview(Pageable pageable, Long userId);
    Slice<ReviewListResponse> findAllTeamReview(Pageable pageable, Long userId);
}
