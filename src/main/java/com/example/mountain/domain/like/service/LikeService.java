package com.example.mountain.domain.like.service;

import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.like.repository.LikeRepository;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.repository.ReviewRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReviewLike (Long reviewId, Long userId) {
        User user = getUser(userId);
        Review review = findReviewBy(reviewId);

        Like reviewLike = likeRepository.findByUserId(userId, reviewId);
        if (reviewLike == null){
            Like like = Like.builder()
                    .review(review)
                    .user(user)
                    .build();
            likeRepository.save(like);
            review.increaseLike();
        }
    }

    @Transactional
    public void deleteReviewLike (Long reviewId, Long userId) {
        User user = getUser(userId);
        Review review = findReviewBy(reviewId);

        Like reviewLike = likeRepository.findByUserId(userId, reviewId);
        if (reviewLike != null){
            likeRepository.deleteByReview_Id(reviewId);
            review.decreaseLike();
        }
    }

    private Review findReviewBy(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_REIVEW));
    }


    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }
}
