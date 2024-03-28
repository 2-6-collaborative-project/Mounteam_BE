package com.example.mountain.domain.team.service;

import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    @Transactional
    public Long create(User user, TeamCreateRequest request){



    }
}
