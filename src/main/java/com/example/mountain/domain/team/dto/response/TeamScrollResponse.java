package com.example.mountain.domain.team.dto.response;

import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamScrollResponse {
    private Long teamId;
    private Author author;
    private String mountain;
    private String title;
    private String content;
    private String gender;
    private List<String> ageRange;
    private String departureDay;
    private LocalDateTime createdAt;
    private boolean createByMe;
    private boolean hasNext;

    public static List<TeamScrollResponse> from(List<Team> teams, boolean hasNext) {
        return teams.stream()
                .map(team -> TeamScrollResponse.builder()
                        .author(Author.from(team.getUser()))
                        .teamId(team.getId())
                        .mountain(team.getMountain().getName())
                        .title(team.getTitle())
                        .content(team.getContent())
                        .gender(team.getGender().toString())
                        .ageRange(team.getAgeRange().stream()
                                .map(Enum::toString)
                                .collect(Collectors.toList()))
                        .departureDay(team.getDepartureDay())
                        .createdAt(team.getCreatedAt())
                        .hasNext(hasNext)
                        .build())
                .collect(Collectors.toList());
    }

}
