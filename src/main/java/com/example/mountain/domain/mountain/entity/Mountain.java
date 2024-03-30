package com.example.mountain.domain.mountain.entity;

import com.example.mountain.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EnableJpaAuditing
public class Mountain {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String explanation;
    private String img;
    private String high;
    private String location;
    private String difficulty;
    private String season;
    private String theme;
    private String longtitue;
    private String lattitue;

    @OneToMany(mappedBy = "mountain")
    private List<Team> teams = new ArrayList<>();
}
