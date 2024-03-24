package com.example.mountain.domain.user.entity;

import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.userFeed.entity.UserFeed;
import com.example.mountain.domain.userMeeting.entity.UserMeeting;
import com.example.mountain.domain.userReview.entity.UserReview;
import com.example.mountain.global.base.BaseEntity;
import com.example.mountain.oauth.OauthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Table(indexes = {
        @Index(columnList = "createDate"),
        @Index(columnList = "userId")
})
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    private Long userId;

    @Setter
    private String nickname;

    @Setter
    private String introduction;

    @Setter
    private String gender;

    @Setter
    private String profileImage;

    @Setter
    private String ageRange;

    @Setter
    private String areaInterest;

    @Setter
    private String userLevel;

    @Setter
    private String locationAgree;

    @Setter
    private String privacyAgree;

    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    @Builder
    private User(Long userId, String nickname, OauthProvider oauthProvider) {
        this.userId = userId;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
    }

    @OneToMany(mappedBy = "user")
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserMeeting> userMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserReview> userReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserFeed> userFeeds = new ArrayList<>();

}
