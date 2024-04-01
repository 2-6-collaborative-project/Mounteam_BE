package com.example.mountain.domain.comment.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedCommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String commenterName;
    private Long feedId;

    public static FeedCommentResponse of(Comment comment, User commenter) {
        return FeedCommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .modifyDate(comment.getModifyDate())
                .commenterName(commenter.getNickname())
                .feedId(comment.getFeed().getId())
                .build();
    }

    public static FeedCommentResponse of(Comment comment) {
        return FeedCommentResponse.builder()
                .content(comment.getContent())
                .feedId(comment.getFeed().getId())
                .createDate(comment.getCreateDate())
                .build();
    }
}
