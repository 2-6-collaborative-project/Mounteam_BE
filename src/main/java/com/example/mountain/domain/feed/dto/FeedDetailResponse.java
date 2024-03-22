package com.example.mountain.domain.feed.dto;

import com.example.mountain.domain.feed.entity.Feed;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class FeedDetailResponse {

    private Long feedId;
    private String content;
    private String nickname;
    private String profilePath;
    private String tag;
//    private String userLevel;
    private Integer likeCnt;
    private Integer commentCnt;

    public static FeedDetailResponse from(Feed feed) {
        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .content(feed.getContent())
                .nickname(feed.getUser().getNickname())
                .profilePath(feed.getUser().getProfileImg())
                .tag(feed.getTag())
                .likeCnt(feed.getLikeCnt())
                .commentCnt(feed.getComments().size())
                .build();
    }
}