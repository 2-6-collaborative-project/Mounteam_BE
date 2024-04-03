package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListScrollResponse;
import org.springframework.data.domain.Pageable;


public interface TeamRepositoryCustom {
    TeamListScrollResponse getMyTeams(Long userId, Pageable pageable, Long cursor);
}
