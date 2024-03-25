package com.example.mountain.domain.comment.dto;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String commenterName;
    private Long feedId;

    public static CommentResponse of(Comment comment, User commenter) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .modifyDate(comment.getModifyDate())
                .commenterName(commenter.getNickname())
                .feedId(comment.getFeed().getId())
                .build();
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .feedId(comment.getFeed().getId())
                .createDate(comment.getCreateDate())
                .build();
    }
}
