package com.example.mountain.domain.user.entity;

import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.userMeeting.entity.UserMeeting;
import com.example.mountain.domain.userReview.entity.UserReview;
import com.example.mountain.global.base.BaseEntity;
import com.example.mountain.oauth.OauthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Setter
@Getter
@Table(indexes = {
        @Index(columnList = "createDate"),
        @Index(columnList = "userId")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    private Long userId;

    private String userAccount;

    private String password;

    private String nickname;

    private String introduction;

    private String gender;

    private String profileImage;

    private String ageRange;

    private String areaInterest;

    private Long userLevel;

    private String locationAgree;

    private String privacyAgree;

    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    @Getter
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserMeeting> userMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserReview> userReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Feed> userFeeds = new ArrayList<>();

    @Builder
    private User(Long userId, String nickname, OauthProvider oauthProvider, String userAccount, String password, Role roles, Long userLevel) {
        this.userId = userId;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.userAccount = userAccount;
        this.password = password;
        this.roles.add(Role.USER);
        this.userLevel = 0L;
    }

    public List<String> getRoleList() {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
