package com.example.mountain.domain.review.repository;

import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.entity.QReview;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.team.entity.QTeam;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mountain.domain.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final EntityManager em;

    @Override
    public Slice<ReviewListResponse> findAllReview(Pageable pageable, Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<Review> countQuery = queryFactory.selectFrom(review);
        long total = countQuery.fetchCount();
        QTeam team = QTeam.team;
        List<Review> reviews = queryFactory.selectFrom(review)
                .where(team.id.isNull())
                .orderBy(review.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewListResponse> reviewListResponses = reviews.stream()
                .map(review -> ReviewListResponse.from(review, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(reviewListResponses, pageable, total);
    }

    @Override
    public Slice<ReviewListResponse> findAllTeamReview (Pageable pageable, Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QTeam team = QTeam.team;

        JPAQuery<Review> countQuery = queryFactory.selectFrom(review)
                .join(review.team, team)
                .where(team.id.isNotNull());

        long total = countQuery.fetchCount();

        List<Review> reviews = queryFactory.selectFrom(review)
                .join(review.team, team)
                .where(team.id.isNotNull())
                .orderBy(review.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewListResponse> reviewListResponses = reviews.stream()
                .map(TeamReview -> ReviewListResponse.from(TeamReview, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(reviewListResponses, pageable, total);
    }
}
