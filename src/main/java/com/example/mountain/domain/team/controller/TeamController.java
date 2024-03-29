package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.dto.TeamDetailResponse;
import com.example.mountain.domain.team.dto.TeamListResponse;
import com.example.mountain.domain.team.dto.TeamUpdateRequest;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "모임 API", description = "모임(Team)")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @Operation(summary = "모임 생성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                       @RequestBody TeamCreateRequest teamCreateRequest) {

        Long teamId = teamService.create(user.getUserId(), teamCreateRequest);
        return GlobalResponse.success(teamId);
    }

    @GetMapping
    @Operation(summary = "모임 전체 조회")
    public GlobalResponse list(){
        TeamListResponse teamList = teamService.findList();

        return GlobalResponse.success(teamList);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "모임 선택 조회")
    public GlobalResponse detail(@PathVariable Long teamId, Long userId){
        TeamDetailResponse teamDetailResponse = teamService.findTeam(teamId, userId);
        return GlobalResponse.success(teamDetailResponse);
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "모임 수정")
    public GlobalResponse update(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user,
                                       @Validated @RequestBody TeamUpdateRequest teamUpdateRequest){

        teamService.update(teamId, user.getUserId(), teamUpdateRequest);
        return GlobalResponse.success("모임이 수정되었습니다.");
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "모임 삭")
    public GlobalResponse delete(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        teamService.delete(teamId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }

}
