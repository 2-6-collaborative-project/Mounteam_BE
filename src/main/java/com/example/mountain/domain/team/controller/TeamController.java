package com.example.mountain.domain.team.controller;

import com.example.mountain.domain.team.dto.request.TeamCreateRequest;
import com.example.mountain.domain.team.dto.response.TeamDetailResponse;
import com.example.mountain.domain.team.dto.response.TeamListResponse;
import com.example.mountain.domain.team.dto.request.TeamUpdateRequest;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.global.aws.S3Service;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "모임 API", description = "모임(Team)")
public class TeamController {

    private final TeamService teamService;
    private final S3Service s3Service;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "모임 생성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                 @RequestPart TeamCreateRequest teamCreateRequest,
                                 @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile) {
        Long teamId;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imageUrl = s3Service.upload(multipartFile, "team");
            teamId = teamService.create(user.getUserId(), teamCreateRequest, imageUrl);
        }else {
            teamId = teamService.create(user.getUserId(), teamCreateRequest, null);
        }

        return GlobalResponse.success(teamId);
    }

    @GetMapping
    @Operation(summary = "모임 전체 조회")
    public GlobalResponse list(@AuthenticationPrincipal CustomUserDetails user){
        List<TeamListResponse> list = teamService.findList(user.getUserId());

        return GlobalResponse.success(list);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "모임 선택 조회")
    public GlobalResponse detail(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        TeamDetailResponse teamDetailResponse = teamService.findTeam(teamId, user.getUserId());
        return GlobalResponse.success(teamDetailResponse);
    }

    @PutMapping(value = "/{teamId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "모임 수정")
    public GlobalResponse update(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user,
                                 @RequestPart TeamUpdateRequest teamUpdateRequest,
                                 @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile){
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imageUrl = s3Service.upload(multipartFile, "team");
            teamService.update(teamId, user.getUserId(), teamUpdateRequest, imageUrl);
        } else {
            teamService.update(teamId, user.getUserId(), teamUpdateRequest);
        }

        return GlobalResponse.success("모임이 수정되었습니다.");
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "모임 삭제")
    public GlobalResponse delete(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails user){
        teamService.delete(teamId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }

}
