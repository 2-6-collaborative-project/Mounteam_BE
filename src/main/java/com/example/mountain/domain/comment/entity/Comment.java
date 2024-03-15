package com.example.mountain.domain.comment.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

}
