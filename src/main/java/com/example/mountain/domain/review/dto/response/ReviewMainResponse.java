package com.example.mountain.domain.review.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.entity.ReviewTagMap;
import com.example.mountain.domain.review.entity.Type;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Getter
@Builder
public class ReviewMainResponse {
    private Author author;
    private Long reviewId;
    private List<String> tags;
    private String mainText;
    private LocalDateTime createdAt;
    private Optional<String> imageUrls;
    private String departureDay;
    private String mountain;
    private int likeCnt;
    private int commentCnt;
    private List<String> comments;
    private Type type;

    public static ReviewMainResponse of(Review review){
        return ReviewMainResponse.builder()
                .reviewId(review.getId())
                .author(Author.from(review.getUser()))
                .mainText(review.getContent())
                .tags(getHashTags(review))
                .createdAt(review.getCreatedAt())
                .mountain(review.getMountain().getName())
                .imageUrls(getImageUrls(review.getImages()))
                .likeCnt(review.getLikeCnt())
                .commentCnt(review.getCommentCnt())
                .type(review.getType())
                .comments(getCommentTexts(review.getComments()))
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
    private static List<String> getCommentTexts(List<Comment> comments) {
        return comments.stream()
                .map(Comment::getContent) // 댓글 내용만 가져오기
                .collect(Collectors.toList());
    }
}
