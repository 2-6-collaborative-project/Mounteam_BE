package com.example.mountain.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FeedListScrollResponse {
    private List<FeedListResponse> feeds;
    private boolean hasNext;
}
