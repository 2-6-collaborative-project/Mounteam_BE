package com.example.mountain.domain.feed.dto.response;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import com.example.mountain.domain.image.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class FeedListResponse {

    private Long feedId;
    private Author author;
    private List<String> tags;
    private String mainText;
    private Integer likesCount;
    private Integer commentCnt;
    private LocalDateTime createdAt;
    private Boolean isLiked;
    private Optional<String> imageUrls;

    public static FeedListResponse from(Feed feed, Long userId) {
        boolean isLiked = feed.getLikes().stream()
                .anyMatch(like -> like.getUser().getUserId().equals(userId));
        return FeedListResponse.builder()
                .feedId(feed.getId())
                .author(Author.from(feed.getUser()))
                .mainText(feed.getContent())
                .tags(getHashTags(feed))
                .likesCount(feed.getLikeCnt())
                .createdAt(feed.getCreateDate())
                .commentCnt(feed.getCommentCnt())
                .isLiked(isLiked)
                .imageUrls(getImageUrls(feed.getImages()))
                .build();
    }

    private static List<String> getHashTags(Feed feed){
        List<String> hashTags = new ArrayList<>();
        for (FeedTagMap feedTagMap : feed.getHashTag()) {
            hashTags.add(feedTagMap.getTag().getName());
        }
        return hashTags;
    }

    private static Optional<String> getImageUrls(List<Image> images) {
        return images.stream()
                .findFirst()
                .map(Image::getImgUrl);
    }
}
