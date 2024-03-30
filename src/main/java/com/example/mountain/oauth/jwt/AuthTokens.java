package com.example.mountain.oauth.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {
    @JsonProperty("isNewUser")
    private boolean isNewUser;
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokens of(boolean isNewUser, String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokens(isNewUser, accessToken, refreshToken, grantType, expiresIn);
    }
}
