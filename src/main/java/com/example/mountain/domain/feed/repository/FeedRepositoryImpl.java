package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.entity.Feed;
import static com.example.mountain.domain.feed.entity.QFeed.feed;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {
    private final EntityManager em;

    @Override
    public Page<FeedListResponse> findAllFeed (Pageable pageable, Long userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<Feed> countQuery = queryFactory.selectFrom(feed);
        long total = countQuery.fetchCount();

        List<Feed> feeds = queryFactory.selectFrom(feed)
                .orderBy(feed.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FeedListResponse> feedListResponses = feeds.stream()
                .map(feed -> FeedListResponse.from(feed, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(feedListResponses, pageable, total);
    }

}
