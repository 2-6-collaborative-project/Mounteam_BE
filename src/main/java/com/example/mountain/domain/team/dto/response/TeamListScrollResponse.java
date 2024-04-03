package com.example.mountain.domain.team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamListScrollResponse {
    private List<TeamListResponse> teams;
    private boolean hasNext;
}
