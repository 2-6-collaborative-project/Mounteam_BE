package com.example.mountain.domain.comment.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FeedCommentsResponse {
    private List<FeedCommentResponse> comments;

    public static FeedCommentsResponse of(List<Comment> comments) {
        return FeedCommentsResponse.builder()
                .comments(comments.stream().map(FeedCommentResponse::of).toList())
                .build();
    }
}
