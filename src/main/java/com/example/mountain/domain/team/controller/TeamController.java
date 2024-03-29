package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
@Tag(name = "모임 API", description = "모임(Team)")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "모임 생성")
    public GlobalResponse<Long> create(@AuthenticationPrincipal CustomUserDetails user,
                                       @RequestBody TeamCreateRequest teamCreateRequest) {

        Long teamId = teamService.create(user.getUser(), teamCreateRequest);
        return GlobalResponse.success(teamId);
    }



}
