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
    private String gender;
    private String chatLink;
    private String chatPassword;
    private String ageRange;
    private LocalDateTime departureDay;

    public static TeamDetailResponse from(Team team) {
        return TeamDetailResponse.builder()
                .teamId(team.getId())
                .title(team.getTitle())
                .content(team.getContent())
                .gender(team.getGender().getValue())
                .chatLink(team.getChatLink())
                .chatPassword(team.getChatPassword())
                .ageRange(team.getAgeRange().getValue())
                .departureDay(team.getDepartureDay())
                .build();
    }
}
