package com.example.mountain.domain.team.dto.response;

import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class TeamInfoResponse {

    private Long teamId;
    private String exploreId;
    private String title;
    private String departureDay;
    private String content;
    private String gender;
    private String ageRange;
    private Author author;

    private LocalDateTime createdAt;

    public static TeamInfoResponse from(Team team) {
        return TeamInfoResponse.builder()
                .teamId(team.getId())
                .exploreId(team.getMountain().getName())
                .departureDay(team.getDepartureDay())
                .title(team.getTitle())
                .content(team.getContent())
                .gender(team.getGender().toString())
                .ageRange(team.getAgeRange().toString())
                .createdAt(team.getCreatedAt())
                .author(Author.from(team.getUser()))
                .build();
    }

}
