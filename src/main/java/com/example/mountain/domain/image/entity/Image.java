package com.example.mountain.domain.image.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Feed feed;

    public Image(String imgUrl, Feed feed){
        this.imgUrl = imgUrl;
        this.feed = feed;
    }

}
