package com.example.mountain.domain.badge.controller;

import com.example.mountain.domain.badge.service.BadgeService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "뱃지 API")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/user/badges")
    @Operation(summary = "뱃지 전체 보기")
    public GlobalResponse getTotalBadge(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        return GlobalResponse.success(badgeService.getTotalBadge(userId));
    }
}
