package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public GlobalResponse<String> create(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody TeamCreateRequest teamCreateRequest) {
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);

//        teamService.create(user, teamCreateRequest);
        return GlobalResponse.success();
    }

}
