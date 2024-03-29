package com.example.mountain.domain.team.dto;

import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class TeamUpdateRequest {
    private String title;
    private String content;
    private Gender gender;
    private String chatLink;
    private String chatPassword;
    private AgeRange ageRange;
    private LocalDateTime departureDay;
}
