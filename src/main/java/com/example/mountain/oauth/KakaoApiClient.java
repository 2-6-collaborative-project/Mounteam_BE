package com.example.mountain.oauth;

import com.example.mountain.oauth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth2.provider.tokenUri}")
    private String tokenUrl;

    @Value("${oauth2.provider.userInfoUri}")
    private String userInfoUrl;

    @Value("${oauth2.kakao.client_id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    public OauthProvider oAuthProvider() {
        return OauthProvider.KAKAO;
    }

    public String requestAccessToken(OAuthLoginParams params) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(tokenUrl, request, KakaoTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(userInfoUrl, request, KakaoInfoResponse.class);
    }
}
