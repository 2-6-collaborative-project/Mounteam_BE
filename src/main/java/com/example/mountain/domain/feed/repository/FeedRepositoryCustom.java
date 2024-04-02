package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedRepositoryCustom{

    Slice<FeedListResponse> findAllFeed(Pageable pageable, Long userId);
    Slice<FeedListResponse> getImagesInFeeds(Long userId, Pageable pageable, Long cursorId);
}
