package com.example.mountain.domain.feed.entity;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.userFeed.entity.UserFeed;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private Integer likeCnt;

    @OneToMany(mappedBy = "feed")
    private List<UserFeed> userFeeds = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<Comment> comments = new ArrayList<>();
}
