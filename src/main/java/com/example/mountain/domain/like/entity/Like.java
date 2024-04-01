package com.example.mountain.domain.like.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean likeCheck; //좋아요 누른 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public Like(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }

    public Like(User user, Review review){
        this.user = user;
        this.review = review;
    }

}
