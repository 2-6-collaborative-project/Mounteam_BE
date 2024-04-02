package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TeamRepositoryCustom {
    Slice<TeamListResponse> getMyTeams(Long userId, Pageable pageable, Long cursor);
}
