package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListScrollResponse;
import com.example.mountain.domain.team.dto.response.TeamScrollResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TeamRepositoryCustom {
    TeamListScrollResponse getMyTeams(Long userId, Pageable pageable, Long cursor);
    List<TeamScrollResponse> getTeamList(Long cursor, Pageable pageable);
}
