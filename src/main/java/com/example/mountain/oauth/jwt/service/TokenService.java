package com.example.mountain.oauth.jwt.service;

import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.oauth.jwt.AuthTokensGenerator;
import com.example.mountain.oauth.jwt.JwtTokenProvider;
import com.example.mountain.oauth.jwt.dto.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokensGenerator authTokensGenerator;

    public AccessTokenResponse createNewAccessToken (String refreshToken) {

        if (!jwtTokenProvider.validToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String userId = jwtTokenProvider.extractSubject(refreshToken);

        return new AccessTokenResponse(authTokensGenerator.newRefreshToken(userId));
    }
}
