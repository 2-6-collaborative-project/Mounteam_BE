package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.dto.TeamDetailResponse;
import com.example.mountain.domain.team.dto.TeamListResponse;
import com.example.mountain.domain.team.dto.TeamUpdateRequest;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "모임 API", description = "모임(Team)")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "모임 생성")
    public GlobalResponse<Long> create(@AuthenticationPrincipal CustomUserDetails user,
                                       @RequestBody TeamCreateRequest teamCreateRequest) {

        Long teamId = teamService.create(user.getUserId(), teamCreateRequest);
        return GlobalResponse.success(teamId);
    }

    @GetMapping
    @Operation(summary = "모임 전체 조회")
    public ResponseEntity<TeamListResponse> list(){
        TeamListResponse teamList = teamService.findList();

        return ResponseEntity.ok(teamList);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "모임 선택 조회")
    public ResponseEntity<TeamDetailResponse> detail(@PathVariable Long teamId, User user){
        return ResponseEntity.ok(teamService.findTeam(teamId, user));
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "모임 수정")
    public String update(@AuthenticationPrincipal CustomUserDetails user,
                                       @Validated @RequestBody TeamUpdateRequest teamUpdateRequest){
        TeamListResponse teamList = teamService.findList();

        return ResponseEntity.ok(teamList);
    }

}
