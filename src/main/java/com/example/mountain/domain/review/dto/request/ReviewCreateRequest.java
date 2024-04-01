package com.example.mountain.domain.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private String mainText;
    @NotNull
    private String mountain;
    @NotNull
    private String departureDay;
    @NotNull
    private boolean agree; //동의

    @Size(max = 10)
    private List<String> hashTags;

    public List<String> getHashTags() {
        return this.hashTags;
    }

}
