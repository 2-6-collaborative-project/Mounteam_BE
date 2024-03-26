package com.example.mountain.domain.feed.repository;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedRepositoryCustom{

    Page<FeedListResponse> findAllFeed(Pageable pageable);
}
