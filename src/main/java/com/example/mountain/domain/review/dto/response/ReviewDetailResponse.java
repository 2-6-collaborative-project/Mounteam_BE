package com.example.mountain.domain.review.dto.response;

import com.example.mountain.domain.feed.dto.response.Author;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.review.entity.ReviewTagMap;
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
public class ReviewDetailResponse {
    private com.example.mountain.domain.feed.dto.response.Author author;
    private Long reviewId;
    private List<String> tags;
    private String mainText;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private String departureDay;
    private String mountain;

    public static ReviewDetailResponse from(Review review, Long userId){
        return ReviewDetailResponse.builder()
                .reviewId(review.getId())
                .author(Author.from(review.getUser()))
                .mainText(review.getContent())
                .tags(getHashTags(review))
                .createdAt(review.getCreateDate())
                .mountain(review.getMountain().getName())
                .imageUrls(getImageUrls(review.getImages()))
                .build();
    }

    private static List<String> getHashTags(Review review){
        List<String> hashTags = new ArrayList<>();
        for (ReviewTagMap reviewTagMap : review.getHashTag()) {
            hashTags.add(reviewTagMap.getTag().getName());
        }
        return hashTags;
    }

    private static List<String> getImageUrls(List<Image> images) {
        return images.stream()
                .map(Image::getImgUrl)
                .collect(Collectors.toList());
    }
}
