package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.badge.service.BadgeService;
import com.example.mountain.domain.team.dto.request.TeamCreateRequest;
import com.example.mountain.domain.team.dto.response.TeamDetailResponse;
import com.example.mountain.domain.team.dto.request.TeamUpdateRequest;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "모임 API", description = "모임(Team)")
public class TeamController {

    private final TeamService teamService;
    private final BadgeService badgeService;

    @PostMapping
    @Operation(summary = "모임 생성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                 @RequestBody TeamCreateRequest teamCreateRequest) {

        Long teamId = teamService.create(user.getUserId(), teamCreateRequest);
        return GlobalResponse.success(teamId);
    }

    @GetMapping
    @Operation(summary = "모임 전체 조회")
    public GlobalResponse getTeamList (@RequestParam(required = false) Long cursor,
                                          @PageableDefault(page = 0, size = 12) Pageable pageable) {
        return GlobalResponse.success(teamService.findPagedTeams(cursor, pageable));
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "모임 선택 조회")
    public GlobalResponse detail(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        TeamDetailResponse teamDetailResponse = teamService.findTeam(teamId, user.getUserId());
        return GlobalResponse.success(teamDetailResponse);
    }

    @GetMapping("/{teamId}/join")
    @Operation(summary = "모임 참가")
    public GlobalResponse<Boolean> valid(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        try {
            boolean valid = teamService.valid(teamId, user.getUserId());
            if (valid) {
                badgeService.receiveBadge(teamId, user.getUserId());
            }
            return GlobalResponse.success(valid);
        } catch (CustomException ex) {
            ErrorCode errorCode = ex.getErrorCode();
            String errorMsg = errorCode.getMessage();
            return GlobalResponse.error(errorMsg, errorCode); // or true depending on your logic
        }
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "모임 수정")
    public GlobalResponse update(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user,
                                 @Validated @RequestBody TeamUpdateRequest teamUpdateRequest){

        teamService.update(teamId, user.getUserId(), teamUpdateRequest);
        return GlobalResponse.success("모임이 수정되었습니다.");
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "모임 삭제")
    public GlobalResponse delete(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        teamService.delete(teamId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }

}
