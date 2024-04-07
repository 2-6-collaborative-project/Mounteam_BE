package com.example.mountain.domain.mountain.entity;

import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mountain {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String explanation;
    private String img;
    private Long high;
    private String location;
    private String difficulty;
    private String season;
    private String theme;
    private double longtitue;
    private double lattitue;

    @OneToMany(mappedBy = "mountain")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "mountain")
    private List<Review> reviews = new ArrayList<>();

}
