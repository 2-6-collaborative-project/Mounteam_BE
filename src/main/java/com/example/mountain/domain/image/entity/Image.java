package com.example.mountain.domain.image.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public Image(String imgUrl, Feed feed){
        this.imgUrl = imgUrl;
        this.feed = feed;
    }

    public Image(String imgUrl, User user){
        this.imgUrl =imgUrl;
        this.user = user;
    }

    public Image(String imgUrl, Review review){
        this.imgUrl = imgUrl;
        this.review = review;
    }

}
