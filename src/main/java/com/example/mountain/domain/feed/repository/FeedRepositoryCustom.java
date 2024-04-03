package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.dto.response.FeedListScrollResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepositoryCustom{

    Slice<FeedListResponse> findAllFeed(Pageable pageable, Long userId);
    FeedListScrollResponse getImagesInFeeds(Long userId, Pageable pageable, Long cursorId);
}
