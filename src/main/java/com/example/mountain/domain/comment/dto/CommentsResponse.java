package com.example.mountain.domain.comment.dto;

import com.example.mountain.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentsResponse {
    private List<CommentResponse> comments;

    public static CommentsResponse of(List<Comment> comments) {
        return CommentsResponse.builder()
                .comments(comments.stream().map(CommentResponse::of).toList())
                .build();
    }
}
