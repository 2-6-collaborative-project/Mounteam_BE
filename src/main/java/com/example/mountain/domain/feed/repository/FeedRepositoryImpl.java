package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.entity.Feed;
import static com.example.mountain.domain.feed.entity.QFeed.feed;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<FeedListResponse> findAllFeed (Pageable pageable, Long userId) {

        JPAQuery<Feed> countQuery = jpaQueryFactory.selectFrom(feed);
        long total = countQuery.fetchCount();

        List<Feed> feeds = jpaQueryFactory.selectFrom(feed)
                .orderBy(feed.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<FeedListResponse> feedListResponses = feeds.stream()
                .map(feed -> FeedListResponse.from(feed, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(feedListResponses, pageable, total);
    }

    @Override
    public Slice<FeedListResponse> getImagesInFeeds(Long userId, Pageable pageable, Long cursorId) {
        List<Feed> content = jpaQueryFactory
                .select(feed)
                .from(feed)
                .where(feed.user.userId.eq(userId),
                        gtTeamId(cursorId))
                .orderBy(feed.id.desc())
                .limit(pageable.getPageSize() +1 )
                .fetch();

        List<FeedListResponse> feedListResponses = content.stream()
                .map(feed -> FeedListResponse.from(feed, userId))
                .collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(feedListResponses, pageable, hasNext);
    }
    private BooleanExpression gtTeamId(Long cursorId) {
        return cursorId == null ? null : feed.id.gt(cursorId);
    }
}
