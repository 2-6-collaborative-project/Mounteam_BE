package com.example.mountain.domain.user.dto;

import com.example.mountain.domain.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class UserMyProfileDto {
    private String nickname;
    private String introduction;
    private String ageRange;
    private String areaInterest;
    private String profileImage;
    private Long userLevel;
    private List<Map<String, String>> badges;

    public UserMyProfileDto (User user, List<Map<String, String>> latestBadges, Long totalBadgeCount) {
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.ageRange = user.getAgeRange();
        this.areaInterest = user.getAreaInterest();
        this.profileImage = user.getProfileImage();
        this.userLevel = totalBadgeCount;
        this.badges = latestBadges;
    }
}
