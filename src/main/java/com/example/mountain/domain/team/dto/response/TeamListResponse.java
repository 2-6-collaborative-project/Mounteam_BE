package com.example.mountain.domain.team.dto.response;

import com.example.mountain.domain.feed.dto.response.Author;
import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TeamListResponse {

    private Long teamId;
    private Author author;
    private String mountain;
    private String title;
    private String content;
    private String gender;
    private List<String> ageRange;
    private String departureDay;
    private LocalDateTime createDate;

    public static List<TeamListResponse> from(List<Team> teams) {
        return teams.stream()
                .map(team -> TeamListResponse.builder()
                        .teamId(team.getId())
                        .mountain(team.getMountain().getName())
                        .title(team.getTitle())
                        .content(team.getContent())
                        .gender(team.getGender().toString())
                        .ageRange(team.getAgeRange().stream()
                                .map(Enum::toString)
                                .collect(Collectors.toList()))
                        .departureDay(team.getDepartureDay())
                        .createDate(team.getCreateDate())
                        .author(Author.from(team.getUser()))
                        .build())
                .collect(Collectors.toList());
    }

}