package com.example.mountain.domain.feed.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Data
public class FeedUpdateRequest {
    private String content;
    private String tag;
    private LocalDateTime modifyAt;
}
