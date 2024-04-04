package com.example.mountain.domain.review.dto.response;

import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.entity.ReviewTagMap;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Builder
public class ReviewListResponse {
    private Author author;
    private Long reviewId;
    private List<String> tags;
    private String mainText;
    private LocalDateTime createdAt;
    private Optional<String> imageUrls;
    private String departureDay;
    private String mountain;
    private boolean createByMe;
    private int likeCnt;
    private int commentCnt;
    private boolean isLiked;

    public static ReviewListResponse from(Review review, Long userId){
        boolean isLiked = isLikedByUser(review, userId);
        return ReviewListResponse.builder()
                .createByMe(review.getUser().getUserId().equals(userId))
                .reviewId(review.getId())
                .author(Author.from(review.getUser()))
                .mainText(review.getContent())
                .tags(getHashTags(review))
                .createdAt(review.getCreateDate())
                .mountain(review.getMountain().getName())
                .imageUrls(getImageUrls(review.getImages()))
                .likeCnt(review.getLikeCnt())
                .commentCnt(review.getCommentCnt())
                .isLiked(isLiked)
                .build();
    }

    public static ReviewListResponse from(Review review){
        return ReviewListResponse.builder()
                .reviewId(review.getId())
                .author(Author.from(review.getUser()))
                .mainText(review.getContent())
                .tags(getHashTags(review))
                .createdAt(review.getCreateDate())
                .mountain(review.getMountain().getName())
                .imageUrls(getImageUrls(review.getImages()))
                .likeCnt(review.getLikeCnt())
                .commentCnt(review.getCommentCnt())
                .build();
    }

    private static List<String> getHashTags(Review review){
        List<String> hashTags = new ArrayList<>();
        for (ReviewTagMap reviewTagMap : review.getHashTag()) {
            hashTags.add(reviewTagMap.getTag().getName());
        }
        return hashTags;
    }

    private static Optional<String> getImageUrls(List<Image> images) {
        return images.stream()
                .findFirst()
                .map(Image::getImgUrl);
    }

    private static boolean isLikedByUser(Review review, Long userId) {
        List<Like> likes = review.getLikes();

        for (Like like : likes) {
            if (like.getUser().getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}
