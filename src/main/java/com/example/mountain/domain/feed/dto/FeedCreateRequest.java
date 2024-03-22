package com.example.mountain.domain.feed.dto;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedCreateRequest {
    @Nullable
    @Size(max=300)
    private String content;
    private String tag;
    private LocalDateTime createdAt;

}
