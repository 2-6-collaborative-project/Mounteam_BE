package com.example.mountain.domain.team.dto.response;

import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class TeamDetailResponse {

    private Long teamId;
    private Author author;
    private String mountain;
    private String title;
    private String content;
    private String gender;
    private List<String> ageRange;
    private String departureDay;
    private LocalDateTime createDate;
    private String chatLink;
    private String chatPassword;
    private boolean createByMe;
    private String imageUrls;

    public static TeamDetailResponse from(Team team, Long userId) {
        boolean createdByMe = team.getUser().getUserId().equals(userId);
        List<String> ageRanges = team.getAgeRange().stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
        return TeamDetailResponse.builder()
                .author(Author.from(team.getUser()))
                .teamId(team.getId())
                .mountain(team.getMountain().getName())
                .title(team.getTitle())
                .content(team.getContent())
                .gender(team.getGender().toString())
                .ageRange(ageRanges)
                .departureDay(team.getDepartureDay())
                .createDate(team.getCreateDate())
                .chatLink(team.getChatLink())
                .chatPassword(team.getChatPassword())
                .createByMe(createdByMe)
                .imageUrls(team.getTeamImage())
                .build();
    }
}
