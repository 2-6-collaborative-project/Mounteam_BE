package com.example.mountain.domain.user.controller;

import com.example.mountain.domain.user.dto.UserMyProfileDto;
import com.example.mountain.domain.user.dto.UserPreferenceDto;
import com.example.mountain.domain.user.dto.UserRequestDto;
import com.example.mountain.domain.user.dto.UserUpdateProfileDto;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import com.example.mountain.oauth.jwt.AuthTokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "유저 API")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    @Operation(summary = "유저 프로필 조회")
    public GlobalResponse<UserMyProfileDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        return GlobalResponse.success(userService.getMyProfile(userId));
    }

    @PostMapping("/user/profile")
    @Operation(summary = "유저 프로필 수정")
    public GlobalResponse<?> updateProfile(@AuthenticationPrincipal CustomUserDetails user,
                                           @RequestBody UserUpdateProfileDto request) {
        Long userId = user.getUserId();
        userService.updateProfile(userId,request);
        return GlobalResponse.success();
    }

    @PostMapping("/user/preferences")
    @Operation(summary = "선호 정보 수집 ")
    public GlobalResponse setPreferences(@AuthenticationPrincipal CustomUserDetails user,
                                            @RequestBody UserPreferenceDto request) {
        Long userId = user.getUserId();
        userService.setPreferences(userId,request);
        return GlobalResponse.success();
    }

    @PostMapping("/signup")
    @Operation(summary = "테스트 토큰 발급 회원가입")
    public GlobalResponse createUser(@RequestBody UserRequestDto requestDto) {
        userService.createUser(requestDto);
        return GlobalResponse.success();
    }

    @PostMapping("/login")
    @Operation(summary = "테스트 로그인 ")
    public GlobalResponse<AuthTokens> loginUser(@RequestBody UserRequestDto.LoginUserDto requestDto) {
        return GlobalResponse.success(userService.loginUser(requestDto));
    }

}
