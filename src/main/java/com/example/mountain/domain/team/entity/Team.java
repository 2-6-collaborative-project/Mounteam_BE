package com.example.mountain.domain.team.entity;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String gender;
    private Integer maxPeople;
    private Integer minPeople;
    private String chatLink;
    private String chatPassword;
    private String ageRange;
    private LocalDateTime departureDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Team_id")
    private Mountain mountain;

    @OneToMany(mappedBy = "team")
    private List<Review> reviews = new ArrayList<>();
}
