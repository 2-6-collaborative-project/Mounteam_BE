package com.example.mountain.domain.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TeamReviewUpdateRequest {

    @NotNull
    private String mainText;
    @Size(max = 10)
    private List<String> hashTags;
    public List<String> getHashTags() {
        return this.hashTags;
    }
}
