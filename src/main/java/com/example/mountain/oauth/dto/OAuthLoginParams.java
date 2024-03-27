package com.example.mountain.oauth.dto;

import com.example.mountain.oauth.OauthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OauthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
