package com.example.mountain.domain.like.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class LikeRequest {
    private Long feedId;
    private Long userId;

    public LikeRequest(Long userId, Long feedId){
        this.userId = userId;
        this.feedId = feedId;
    }
}
