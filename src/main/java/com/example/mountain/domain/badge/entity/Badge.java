package com.example.mountain.domain.badge.entity;

import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "created_at")
})
public class Badge extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String badgeName;
    private String badgeImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Badge(String badgeName, String badgeImg, User user) {
        this.badgeName = badgeName;
        this.badgeImg = badgeImg;
        this.user = user;
    }

    public static Badge createBadge(String badgeName, String badgeImg, User user) {
        return new Badge(badgeName, badgeImg, user);
    }

}
