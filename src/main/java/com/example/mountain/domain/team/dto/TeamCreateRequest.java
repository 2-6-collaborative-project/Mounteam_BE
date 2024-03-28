package com.example.mountain.domain.team.dto;

import java.time.LocalDateTime;

public class TeamCreateRequest {
    private Long teamId;
    private String title;
    private String content;
    private String gender;
    private Integer maxPeople;
    private Integer minPeople;
    private String chatLink;
    private String chatPassword;
    private String ageRange;
    private LocalDateTime departureDay;
}
