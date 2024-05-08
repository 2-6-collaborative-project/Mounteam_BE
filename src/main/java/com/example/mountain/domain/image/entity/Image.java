package com.example.mountain.domain.image.entity;

import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;
    private String teamImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    public Image(String imgUrl, User user){
        this.imgUrl =imgUrl;
        this.user = user;
    }

    public Image(String imgUrl, Review review){
        this.imgUrl = imgUrl;
        this.review = review;
    }

}
