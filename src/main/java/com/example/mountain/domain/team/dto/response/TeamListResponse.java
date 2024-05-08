package com.example.mountain.domain.team.dto.response;

import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.team.entity.Team;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Builder
@Getter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamListResponse {

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
    private String imageUrls;

    public static List<TeamListResponse> from(List<Team> teams, Long userId) {
        return teams.stream()
                .map(team -> TeamListResponse.builder()
                        .author(Author.from(team.getUser()))
                        .teamId(team.getId())
                        .mountain(team.getMountain().getName())
                        .title(team.getTitle())
                        .content(team.getContent())
                        .imageUrls(team.getTeamImage())
                        .gender(team.getGender().toString())
                        .ageRange(team.getAgeRange().stream()
                                .map(Enum::toString)
                                .collect(Collectors.toList()))
                        .departureDay(team.getDepartureDay())
                        .createdAt(team.getCreatedAt())
                        .createByMe(team.getUser().getUserId().equals(userId))
                        .build())
                .collect(Collectors.toList());
    }
    private static Optional<String> getImageUrls(List<Image> images) {
        return images.stream()
                .findFirst()
                .map(Image::getImgUrl);
    }

}