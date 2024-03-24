package com.example.mountain.domain.feed.dto;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedCreateRequest {
    @Nullable
    @Size(max = 300)
    private String content;
    @Size(max = 10)
    private List<@Size(max= 10) String> hashTags;
    private LocalDateTime createdAt;

    private List<@Size(max= 5) String> images;

    public List<String> hashTags () {
        return this.hashTags;
    }

    public List<String> images(){
        return this.images;
    }
}
