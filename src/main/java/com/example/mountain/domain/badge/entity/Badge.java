package com.example.mountain.domain.badge.entity;

import com.example.mountain.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class Badge {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String badgeName;
    private String badgeImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
