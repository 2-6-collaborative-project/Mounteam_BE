package com.example.mountain.domain.feed.dto.response;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import com.example.mountain.domain.image.entity.Image;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Getter
public class FeedDetailResponse {

    private Long feedId;
    private Author author;
    private String mainText;
    private List<String> tags;
    private List<String> comments;
    private Integer likesCount;
    private Integer commentCnt;
    private Boolean createdByMe;
    private LocalDateTime createdAt;
    private Boolean isLiked;
    private List<String> imageUrls;

    public static FeedDetailResponse from(Feed feed, Long userId) {
        boolean createdByMe = feed.getUser().getUserId().equals(userId);
        boolean isLiked = feed.getLikes().stream()
                .anyMatch(like -> like.getUser().getUserId().equals(userId));
        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .author(Author.from(feed.getUser()))
                .createdByMe(createdByMe)
                .mainText(feed.getContent())
                .tags(getHashTags(feed))
                .likesCount(feed.getLikeCnt())
                .createdAt(feed.getCreateDate())
                .comments(getCommentTexts(feed.getComments()))
                .isLiked(isLiked)
                .commentCnt(feed.getCommentCnt())
                .imageUrls(getImageUrls(feed.getImages()))
                .build();
    }

    private static List<String> getImageUrls(List<Image> images) {
        return images.stream()
                .map(Image::getImgUrl)
                .collect(Collectors.toList());
    }


    private static List<String> getHashTags(Feed feed){
        List<String> hashTags = new ArrayList<>();
        for (FeedTagMap feedTagMap : feed.getHashTag()) {
            hashTags.add(feedTagMap.getTag().getName());
        }
        return hashTags;
    }

    private static List<String> getCommentTexts(List<Comment> comments) {
        return comments.stream()
                .map(Comment::getContent) // 댓글 내용만 가져오기
                .collect(Collectors.toList());
    }
}