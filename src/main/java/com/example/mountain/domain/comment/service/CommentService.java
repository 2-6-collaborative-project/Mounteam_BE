package com.example.mountain.domain.comment.service;

import com.example.mountain.domain.comment.dto.request.CommentRequest;
import com.example.mountain.domain.comment.dto.response.ReviewCommentResponse;
import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.comment.repository.CommentRepository;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.repository.ReviewRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    @Transactional
    public Long createReviewComment (Long userId, Long reviewId, CommentRequest commentRequest) {
        User user = getUser(userId);
        LocalDateTime now = LocalDateTime.now();
        Review review = findReviewBy(reviewId);

        Comment comment = Comment.create(user, commentRequest.getContent(), review, now);
        Comment savedComment = commentRepository.save(comment);
        review.increaseComment();
        return savedComment.getId();
    }


    @Transactional(readOnly = true)
    public List<ReviewCommentResponse> getReviewCommentsWithUsers(Long reviewId) {
        return commentRepository.findReviewCommentsWithUsers(reviewId);
    }


    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }
    private Review findReviewBy(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_REIVEW));
    }


}
