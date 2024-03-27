package com.example.mountain.oauth.service;

import com.example.mountain.domain.user.entity.Role;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.oauth.dto.OAuthInfoResponse;
import com.example.mountain.oauth.dto.OAuthLoginParams;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.jwt.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OauthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long userId = findOrCreateUser(oAuthInfoResponse);
        return authTokensGenerator.generate(userId);
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


}
