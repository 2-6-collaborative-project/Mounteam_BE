package com.example.mountain.domain.feed.dto.response;

import com.example.mountain.domain.feed.entity.Feed;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class FeedListResponse {
    List<FeedDetailResponse> feeds;

    public static FeedListResponse from(List<Feed> feeds){
        List<FeedDetailResponse> feedDetails = feeds.stream()
                .map(FeedDetailResponse::from)
                .collect(Collectors.toList());
        return new FeedListResponse(feedDetails);
    }
}
