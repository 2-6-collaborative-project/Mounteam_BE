package com.example.mountain.domain.feed.dto;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
public class FeedDetailResponse {

    private Long feedId;
    private String content;
    private String nickname;
    private String profilePath;
    private List<String> hashTags;
    //    private String userLevel;
    private List<String> comments;
    private Integer likeCnt;
    private Integer commentCnt;

    public static FeedDetailResponse from(Feed feed) {
        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .content(feed.getContent())
                .nickname(feed.getUser().getNickname())
                .hashTags(getHashTags(feed))
                .likeCnt(feed.getLikeCnt())
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