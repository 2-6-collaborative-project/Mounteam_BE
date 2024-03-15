package com.example.mountain.domain.userReview.entity;

import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class UserReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

}
