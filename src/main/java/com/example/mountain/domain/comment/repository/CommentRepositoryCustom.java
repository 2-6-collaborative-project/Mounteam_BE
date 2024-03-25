package com.example.mountain.domain.comment.repository;

import com.example.mountain.domain.comment.dto.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponse> findCommentsWithUsers(Long feedId);
}
