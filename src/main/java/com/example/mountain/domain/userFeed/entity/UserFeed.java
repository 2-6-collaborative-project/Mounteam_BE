package com.example.mountain.domain.userFeed.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class UserFeed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;
}
