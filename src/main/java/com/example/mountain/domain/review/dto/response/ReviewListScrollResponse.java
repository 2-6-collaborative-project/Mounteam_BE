package com.example.mountain.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ReviewListScrollResponse {
    private List<ReviewListResponse> reviews;
    private boolean hasNext;
}
