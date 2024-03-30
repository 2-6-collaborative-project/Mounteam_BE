package com.example.mountain.domain.team.dto.request;

import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TeamUpdateRequest {
    private String title;
    private String content;
    private String gender;
    private String chatLink;
    private String chatPassword;
    private List<String> ageRange;
    private String departureDay;

}
