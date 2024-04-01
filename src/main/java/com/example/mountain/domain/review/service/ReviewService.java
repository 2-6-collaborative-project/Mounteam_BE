package com.example.mountain.domain.review.service;

import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.image.repository.ImageRepository;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.review.dto.request.ReviewCreateRequest;
import com.example.mountain.domain.review.dto.request.ReviewUpdateRequest;
import com.example.mountain.domain.review.dto.response.ReviewDetailResponse;
import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.entity.ReviewTagMap;
import com.example.mountain.domain.review.repository.ReviewRepository;
import com.example.mountain.domain.review.repository.ReviewTagRepository;
import com.example.mountain.domain.tag.entity.Tag;
import com.example.mountain.domain.tag.repostiory.TagRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MountainRepository mountainRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long create (Long userId, ReviewCreateRequest request, List<String> imgPaths) {
        postBlankCheck(imgPaths);
        User user = getUser(userId);
        Mountain mountain = getMountain(request.getMountain());

        Review review = Review.of(request, user, mountain);
        createHashTag(request.getHashTags(),review);

        Review savedReview = reviewRepository.save(review);
        List<String> imgList = new ArrayList<>();
        for (String imgUrl : imgPaths) {
            Image image = new Image(imgUrl, savedReview);
            imageRepository.save(image);
            imgList.add(image.getImgUrl());
        }
        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public Page<ReviewListResponse> findList(Pageable pageable, Long userId){
        Page<ReviewListResponse> reviewListResponse = reviewRepository.findAllReview(pageable, userId);
        return reviewListResponse;
    }
    @Transactional(readOnly = true)
    public ReviewDetailResponse findReview (Long reviewId, Long userId) {
        Review review = findReviewBy(reviewId);
        return ReviewDetailResponse.from(review, userId);
    }

    @Transactional
    public void update (Long reviewId, Long userId, ReviewUpdateRequest reviewUpdateRequest,List<String> imgPaths) {
        User user = getUser(userId);
        Review review = getReview(reviewId);
        Mountain mountain = getMountain(reviewUpdateRequest.getMountain());
        if (review.getUser().equals(user)) {
            review.update(reviewUpdateRequest, mountain);
            if (imgPaths != null) {
                List<String> imgList = new ArrayList<>();
                for (String imgUrl : imgPaths) {
                    Image image = new Image(imgUrl, review);
                    imageRepository.save(image);
                    imgList.add(image.getImgUrl());
                }
            }
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_REVIEW_USER_UPDATE);
        }
    }

    @Transactional
    public void delete (Long reviewId, Long userId) {
        User user = getUser(userId);
        Review review = getReview(reviewId);
        if(review.getUser().getUserId().equals(user.getUserId())){
            reviewRepository.delete(review);
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_REVIEW_USER_DELETE);
        }
    }

    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }
    private Mountain getMountain (String mountainName) {
        Mountain mountain = mountainRepository.findByName(mountainName)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
        return mountain;
    }
    private Review getReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_REIVEW));
        return review;
    }
    private void createHashTag(List<String> hashtags, Review review) {
        List<ReviewTagMap> reviewTagMaps = hashtags.stream()
                .map(name -> saveTag(name))
                .map(tag -> ReviewTagMap.createReviewTag(tag, review))
                .collect(Collectors.toList());
        reviewTagRepository.saveAll(reviewTagMaps);
    }
    private Tag saveTag(String name) {
        return tagRepository
                .findByName(name)
                .orElseGet(() -> tagRepository.save(Tag.builder()
                        .name(name)
                        .build()));
    }
    private void postBlankCheck(List<String> imgPaths) {
        if(imgPaths == null || imgPaths.isEmpty()){ //.isEmpty()도 되는지 확인해보기
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
    }
    private Review findReviewBy(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_REIVEW));
    }


}
