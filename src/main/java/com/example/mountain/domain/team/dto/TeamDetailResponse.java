package com.example.mountain.domain.team.dto;

import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TeamDetailResponse {

    private Long teamId;
    private String title;
    private String content;
    private Gender gender;
    private String chatLink;
    private String chatPassword;
    private AgeRange ageRange;
    private LocalDateTime departureDay;

    public static TeamDetailResponse from(Team team) {
        return TeamDetailResponse.builder()
                .teamId(team.getId())
                .title(team.getTitle())
                .content(team.getContent())
                .gender(team.getGender())
                .chatLink(team.getChatLink())
                .chatPassword(team.getChatPassword())
                .ageRange(team.getAgeRange())
                .departureDay(team.getDepartureDay())
                .build();
    }
}
