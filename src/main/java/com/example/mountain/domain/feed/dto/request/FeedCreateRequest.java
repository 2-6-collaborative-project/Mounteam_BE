package com.example.mountain.domain.feed.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedCreateRequest {
    @Nullable
    @Size(max = 300)
    private String content;
    @Size(max = 10)
    private List<@Size(max= 10) String> hashTags; //값 제한수정필요
    public List<String> hashTags () {
        return this.hashTags;
    }
}
