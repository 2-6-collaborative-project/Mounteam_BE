package com.example.mountain.domain.feed.dto;

import com.example.mountain.domain.feed.entity.Feed;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FeedListResponse {
    List<FeedDetailResponse> feeds;

    public static FeedListResponse from(List<Feed> feeds){
        List<FeedDetailResponse> feedDetails = feeds.stream()
                .map(FeedDetailResponse::from)
                .toList();
        return new FeedListResponse(feedDetails);
    }
}
