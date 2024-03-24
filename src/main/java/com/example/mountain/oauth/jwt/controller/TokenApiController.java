package com.example.mountain.oauth.jwt.controller;

import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.oauth.jwt.dto.AccessTokenRequest;
import com.example.mountain.oauth.jwt.dto.AccessTokenResponse;
import com.example.mountain.oauth.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/token")
    @Operation(summary = "access token 발급")
    public GlobalResponse<AccessTokenResponse> createNewAccessToken(@RequestBody AccessTokenRequest request) {

        AccessTokenResponse accessTokenResponse
                = tokenService.createNewAccessToken(request.getRefreshToken());
        return GlobalResponse.success(accessTokenResponse);
    }
}
