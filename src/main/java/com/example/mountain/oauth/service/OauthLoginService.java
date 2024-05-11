package com.example.mountain.oauth.service;

import com.example.mountain.domain.user.entity.Role;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.oauth.dto.OAuthInfoResponse;
import com.example.mountain.oauth.dto.OAuthLoginParams;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.jwt.AuthTokensGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
@Transactional
public class OauthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    @Value("${oauth2.kakao.admin_id}")
    private String kakaoAdminId;

    private final String url = "https://kapi.kakao.com/v1/user/logout";

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = findOrCreateUser(oAuthInfoResponse);
        boolean isNewUser = isNewUser(userId);
        AuthTokens authTokens = authTokensGenerator.generate(userId, isNewUser);
        return authTokens;
    }

    public String logout() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAdminId);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>("target_id_type=user_id&target_id=" + kakaoAdminId, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

    private Long findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findById(oAuthInfoResponse.getUserId())
                .map(User::getUserId)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .userId(oAuthInfoResponse.getUserId())
                .nickname(oAuthInfoResponse.getNickname())
                .oauthProvider(oAuthInfoResponse.getOAuthProvider())
                .roles(Role.USER)
                .build();

        return userRepository.save(user).getUserId();
    }

    private boolean isNewUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        return user.getPrivacyAgree() == null ? true : false;
    }

}
