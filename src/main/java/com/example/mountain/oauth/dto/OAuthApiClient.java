package com.example.mountain.oauth.dto;

import com.example.mountain.oauth.OauthProvider;

public interface OAuthApiClient {
    OauthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
