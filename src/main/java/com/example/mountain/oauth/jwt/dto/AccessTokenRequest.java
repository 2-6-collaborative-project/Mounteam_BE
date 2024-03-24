package com.example.mountain.oauth.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenRequest {
    private String refreshToken;
}
