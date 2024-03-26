package com.example.mountain.domain.mountain.entity;

import com.example.mountain.domain.team.entity.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Mountain {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String img;
    private String high;
    private String location;
    private String level;
    private Integer numberCourse;
    private String elapsedTime;

    @OneToMany(mappedBy = "mountain")
    private List<Team> teams = new ArrayList<>();
}
