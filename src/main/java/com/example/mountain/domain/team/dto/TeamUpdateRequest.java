package com.example.mountain.domain.team.dto;

import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class TeamUpdateRequest {
    private String title;
    private String content;
    private String gender;
    private String chatLink;
    private String chatPassword;
    private String ageRange;
    private LocalDateTime departureDay;

}
