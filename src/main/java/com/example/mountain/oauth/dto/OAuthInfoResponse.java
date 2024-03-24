package com.example.mountain.oauth.dto;

import com.example.mountain.oauth.OauthProvider;

import java.time.LocalDateTime;

public interface OAuthInfoResponse {
    Long getUserId();
    String getNickname();
    OauthProvider getOAuthProvider();
}