package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.dto.response.ReviewListScrollResponse;
import com.example.mountain.domain.review.dto.response.ReviewMainResponse;
import com.example.mountain.domain.review.dto.response.ReviewMainScrollResponse;
import com.example.mountain.domain.review.entity.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mountain.domain.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ReviewListScrollResponse findAllReview(Pageable pageable, Long userId) {

        List<Review> reviews = jpaQueryFactory.
                selectFrom(review)
                .orderBy(review.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (reviews.size() > pageable.getPageSize()) {
            reviews.remove(pageable.getPageSize());
            hasNext = true;
        }
        List<ReviewListResponse> reviewListResponses = reviews.stream()
                .map(review -> ReviewListResponse.from(review, userId))
                .collect(Collectors.toList());

        return new ReviewListScrollResponse(reviewListResponses, hasNext);
    }

    @Override
    public ReviewMainScrollResponse findAllReview(Pageable pageable) {

        List<Review> reviews = jpaQueryFactory.
                selectFrom(review)
                .orderBy(review.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (reviews.size() > pageable.getPageSize()) {
            reviews.remove(pageable.getPageSize());
            hasNext = true;
        }
        List<ReviewMainResponse> reviewListResponses = reviews.stream()
                .map(review -> ReviewMainResponse.of(review))
                .collect(Collectors.toList());

        return new ReviewMainScrollResponse(reviewListResponses, hasNext);
    }

    @Override
    public ReviewListScrollResponse getImagesInReviews (Long userId, Pageable pageable, Long cursorId) {
        List<Review> content = jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.user.userId.eq(userId),
                        ltReviewId(cursorId))
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize() +1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<ReviewListResponse> reviewListResponses = content.stream()
                .map(review -> ReviewListResponse.from(review, userId))
                .collect(Collectors.toList());

        return new ReviewListScrollResponse(reviewListResponses, hasNext);
    }
    private BooleanExpression ltReviewId(Long cursorId) {
        return cursorId == null ? null : review.id.lt(cursorId);
    }
}
