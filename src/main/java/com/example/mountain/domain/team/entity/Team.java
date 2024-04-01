package com.example.mountain.domain.team.entity;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.review.entity.Review;
import com.example.mountain.domain.team.dto.request.TeamUpdateRequest;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String chatLink;
    private String chatPassword;
    @ElementCollection(targetClass = AgeRange.class)
    @Enumerated(EnumType.STRING)
    private List<AgeRange> ageRange;

    private String departureDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    public void update(TeamUpdateRequest teamUpdateRequest){
        this.content = teamUpdateRequest.getContent();
        this.title = teamUpdateRequest.getTitle();
        this.gender = Gender.valueOf(teamUpdateRequest.getGender());
        this.chatLink = teamUpdateRequest.getChatLink();
        this.chatPassword = teamUpdateRequest.getChatPassword();
        List<AgeRange> ageRanges = new ArrayList<>();
        for (String value : teamUpdateRequest.getAgeRange()) {
            ageRanges.add(AgeRange.fromString(value));
        }
        this.ageRange = ageRanges;
        this.departureDay = teamUpdateRequest.getDepartureDay();
    }
}
