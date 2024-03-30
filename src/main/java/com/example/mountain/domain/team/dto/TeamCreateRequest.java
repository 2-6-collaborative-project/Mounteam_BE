package com.example.mountain.domain.team.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TeamCreateRequest {
    @NotNull
    private String mountain;
    @Size(min = 5, max = 10)
    private String title;
    @Size(min = 10)
    private String content;
    @NotNull
    private String gender;
    @NotNull
    private String chatLink;
    @NotNull
    private String chatPassword;
    @NotNull
    private List<String> ageRange;
    @NotNull
    private String departureDay;


}
