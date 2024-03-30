package com.example.mountain.domain.feed.entity;

import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.tag.entity.Tag;
import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.feed.dto.request.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.request.FeedUpdateRequest;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EnableJpaAuditing
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    private String content;
    @Builder.Default
    private int likeCnt = 0;

    //댓글갯수
    @Builder.Default
    private int commentCnt = 0;

    private boolean createdByMe;

    private boolean isLiked;

    private boolean isSaved;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedTagMap> hashTag = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public static Feed of(FeedCreateRequest feedCreateRequest, User user, LocalDateTime now){
        return Feed.builder()
                .content(feedCreateRequest.getContent())
                .createDate(now)
                .user(user)
                .build();
    }

    public void update(FeedUpdateRequest updateRequest){
        this.content = updateRequest.getContent();
//        this.modifyDate = updateRequest.getModifyAt();
    }

    // 게시물에 해시태그 추가
    public void addHashTag(Tag tag) {
        FeedTagMap feedTagMap = FeedTagMap.createPostHashtag(tag, this);
        this.hashTag.add(feedTagMap);
    }

    // 게시물에 해시태그 삭제
    public void removeHashTag(Tag tag) {
        this.hashTag.removeIf(feedTagMap -> feedTagMap.getTag().equals(tag));
    }

    public void increaseComment() {
        this.commentCnt++;
    }

    public void decreaseComment() {
        if (this.commentCnt > 0){
            commentCnt --;
        }
    }

    public void increaseLike(){
        this.likeCnt++;
    }
    public void decreaseLike(){
        if (this.likeCnt > 0){
            likeCnt --;
        }
    }
}
