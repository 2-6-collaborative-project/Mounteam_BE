package com.example.mountain.domain.user.entity;

import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.userFeed.entity.UserFeed;
import com.example.mountain.domain.userMeeting.entity.UserMeeting;
import com.example.mountain.domain.userReview.entity.UserReview;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String socialId;
    private String password;
    private String name;
    private String nickname;
    private String gender;
    private String profileImg;
    private String ageRange;
    private String areaInterest;
    private String climbLevel;
    private boolean locationAgree;
    private boolean privacyAgree;

    @OneToMany(mappedBy = "user")
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserMeeting> userMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserReview> userReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserFeed> userFeeds = new ArrayList<>();

}
