package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.entity.Review;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mountain.domain.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final EntityManager em;

    @Override
    public Page<ReviewListResponse> findAllReview(Pageable pageable, Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<Review> countQuery = queryFactory.selectFrom(review);
        long total = countQuery.fetchCount();

        List<Review> reviews = queryFactory.selectFrom(review)
                .orderBy(review.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewListResponse> reviewListResponses = reviews.stream()
                .map(review -> ReviewListResponse.from(review, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(reviewListResponses, pageable, total);
    }
}
