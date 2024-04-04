package com.example.mountain.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ReviewMainScrollResponse {
    private List<ReviewMainResponse> reviews;
    private boolean hasNext;
}
