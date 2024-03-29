package com.example.mountain.domain.feed.dto.request;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Data
public class FeedUpdateRequest {
    private String content;
    private String tag;

}
