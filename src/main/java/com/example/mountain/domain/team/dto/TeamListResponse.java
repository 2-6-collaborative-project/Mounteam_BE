package com.example.mountain.domain.team.dto;

import com.example.mountain.domain.team.entity.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamListResponse {
    List<TeamDetailResponse> teams;

    public static TeamListResponse from(List<Team> teams){
        List<TeamDetailResponse> teamDetails = teams.stream()
                .map(TeamDetailResponse::from)
                .toList();
        return new TeamListResponse(teamDetails);
    }
}