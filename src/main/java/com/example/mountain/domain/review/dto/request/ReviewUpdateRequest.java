package com.example.mountain.domain.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class ReviewUpdateRequest {

    private String mainText;
    private String mountain;
    private String departureDay;
    @Size(max = 10)
    private List<String> hashTags;

    public List<String> getHashTags() {
        return this.hashTags;
    }

}
