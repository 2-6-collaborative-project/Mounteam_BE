package com.example.mountain.domain.feed.dto.response;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class FeedListResponse {

    private Long feedId;
    private Author author;
    private List<String> tags;
    private String mainText;
    private Integer likesCount;
    private Integer commentCnt;
    private Boolean createdByMe;
    private LocalDateTime createdAt;
    private Boolean isLiked;

    public static FeedListResponse from(Feed feed, Long userId) {
        boolean createdByMe = feed.getUser().getUserId().equals(userId);
        boolean isLiked = feed.getLikes().stream()
                .anyMatch(like -> like.getUser().getUserId().equals(userId));
        return FeedListResponse.builder()
                .feedId(feed.getId())
                .author(Author.from(feed.getUser()))
                .mainText(feed.getContent())
                .tags(getHashTags(feed))
                .likesCount(feed.getLikeCnt())
                .createdAt(feed.getCreateDate())
                .createdByMe(createdByMe)
                .commentCnt(feed.getCommentCnt())
                .isLiked(isLiked)
                .build();
    }

    private static List<String> getHashTags(Feed feed){
        List<String> hashTags = new ArrayList<>();
        for (FeedTagMap feedTagMap : feed.getHashTag()) {
            hashTags.add(feedTagMap.getTag().getName());
        }
        return hashTags;
    }
}
