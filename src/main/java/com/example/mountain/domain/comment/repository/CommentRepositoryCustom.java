package com.example.mountain.domain.comment.repository;

import com.example.mountain.domain.comment.dto.response.ReviewCommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<ReviewCommentResponse> findReviewCommentsWithUsers(Long reviewId);
}
