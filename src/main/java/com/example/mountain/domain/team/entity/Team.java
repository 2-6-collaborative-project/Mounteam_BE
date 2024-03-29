package com.example.mountain.domain.team.entity;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.team.dto.TeamUpdateRequest;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EnableJpaAuditing
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String chatLink;
    private String chatPassword;
    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    private LocalDateTime departureDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    public void update(TeamUpdateRequest teamUpdateRequest){
        this.content = teamUpdateRequest.getContent();
        this.title = teamUpdateRequest.getTitle();
        this.gender = teamUpdateRequest.getGender();
        this.chatLink = teamUpdateRequest.getChatLink();
        this.chatPassword = teamUpdateRequest.getChatPassword();
        this.ageRange = teamUpdateRequest.getAgeRange();
        this.departureDay = teamUpdateRequest.getDepartureDay();
    }


}
