package com.example.mountain.domain.comment.repository;

import com.example.mountain.domain.comment.dto.response.FeedCommentResponse;
import com.example.mountain.domain.comment.dto.response.ReviewCommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<FeedCommentResponse> findFeedCommentsWithUsers(Long feedId);
    List<ReviewCommentResponse> findReviewCommentsWithUsers(Long reviewId);
}
