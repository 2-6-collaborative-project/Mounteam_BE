package com.example.mountain.oauth.controller;


import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.oauth.dto.KakaoLoginParams;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.service.OauthLoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OauthController {

    private final OauthLoginService oauthLoginService;

    @PostMapping("/kakao")
    @Operation(summary = "kakao 로그인 ")
    public GlobalResponse<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {

        return GlobalResponse.success(oauthLoginService.login(params));
    }

}
