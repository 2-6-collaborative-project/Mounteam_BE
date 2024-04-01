package com.example.mountain.domain.review.entity;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.review.dto.request.ReviewCreateRequest;
import com.example.mountain.domain.review.dto.request.ReviewUpdateRequest;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTagMap> hashTag = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    private String departureDay;

    private boolean agree; //위치정보,날 서비스 동의

    @Builder.Default
    private int likeCnt = 0;

    //댓글갯수
    @Builder.Default
    private int commentCnt = 0;

    private boolean createdByMe;

    private boolean isLiked;

    private boolean isSaved;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static Review of(ReviewCreateRequest reviewCreateRequest, User user, Mountain mountain){
        return Review.builder()
                .content(reviewCreateRequest.getMainText())
                .user(user)
                .mountain(mountain)
                .departureDay(reviewCreateRequest.getDepartureDay())
                .agree(reviewCreateRequest.isAgree())
                .build();

    }
    public void increaseComment() {
        this.commentCnt++;
    }

    public void decreaseComment() {
        if (this.commentCnt > 0){
            this.commentCnt --;
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

    public void update (ReviewUpdateRequest reviewUpdateRequest, Mountain mountain) {
        this.content = reviewUpdateRequest.getMainText();
        this.mountain = mountain;
        this.departureDay = reviewUpdateRequest.getDepartureDay();
    }
}
