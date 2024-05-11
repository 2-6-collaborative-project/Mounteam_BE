package com.example.mountain.oauth.controller;


import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.oauth.dto.KakaoLoginParams;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.service.OauthLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Oauth API")
public class OauthController {

    private final OauthLoginService oauthLoginService;

    @PostMapping("/kakao")
    @Operation(summary = "kakao 로그인 ")
    public GlobalResponse<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {

        return GlobalResponse.success(oauthLoginService.login(params));
    }

    @GetMapping("/kakao-logout")
    @Operation(summary = "kakao 로그아웃")
    public GlobalResponse<?> logoutKakao() {
        return GlobalResponse.success(oauthLoginService.logout());
    }

}
