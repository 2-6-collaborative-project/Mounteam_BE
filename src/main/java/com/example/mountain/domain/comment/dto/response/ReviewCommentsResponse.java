package com.example.mountain.domain.comment.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewCommentsResponse {
    private List<ReviewCommentResponse> comments;

    public static ReviewCommentsResponse of(List<Comment> comments) {
        return ReviewCommentsResponse.builder()
                .comments(comments.stream().map(ReviewCommentResponse::of).toList())
                .build();
    }
}
