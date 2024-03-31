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
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Transactional
public class OauthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = findOrCreateUser(oAuthInfoResponse);
        boolean isNewUser = isNewUser(userId);
        AuthTokens authTokens = authTokensGenerator.generate(userId, isNewUser);
        return authTokens;
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
