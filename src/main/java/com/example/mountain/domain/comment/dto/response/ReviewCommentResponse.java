package com.example.mountain.domain.comment.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewCommentResponse {

    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String commenterName;
    private Long reviewId;

    public static ReviewCommentResponse of(Comment comment, User commenter) {
        return ReviewCommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .modifyDate(comment.getModifyDate())
                .commenterName(commenter.getNickname())
                .reviewId(comment.getReview().getId())
                .build();
    }

    public static ReviewCommentResponse of(Comment comment) {
        return ReviewCommentResponse.builder()
                .content(comment.getContent())
                .reviewId(comment.getReview().getId())
                .createDate(comment.getCreateDate())
                .build();
    }
}
