package com.example.mountain.domain.user.service;


import com.example.mountain.domain.user.dto.UserPreferenceDto;
import com.example.mountain.domain.user.dto.UserRequestDto;
import com.example.mountain.domain.user.entity.Role;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.jwt.AuthTokensGenerator;
import com.example.mountain.oauth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;


@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthTokensGenerator authTokensGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    public void createUser(UserRequestDto requestDto) {
        User user = User.builder()
                        .userId(generateID())
                        .userAccount(requestDto.getAccount())
                        .nickname(requestDto.getNickname())
                        .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                        .roles(Role.USER)
                        .build();
        userRepository.save(user);
    }

    public AuthTokens loginUser(UserRequestDto.LoginUserDto requestDto) {
        User user = userRepository.findByUserAccount(requestDto.getAccount())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        boolean isNewUser = user.getPrivacyAgree() == null ? true : false;
        return authTokensGenerator.generate(user.getUserId(), isNewUser);
    }

    public Long generateID() {
        long randomNumber = ThreadLocalRandom.current().nextLong(1, 2001);
        long randomId = Math.abs(randomNumber);

        if (userRepository.findById(randomId).isPresent() ) {
            return generateID();
        }
        return randomId;
    }

    public String findByUser(String token) {
        String result = token.substring("Bearer ".length());
        Long userId = Long.parseLong(jwtTokenProvider.extractSubject(result));
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user.getNickname();
    }

    public User getUserFromToken(String token) {
        if (token != null) {
            String userIdString = jwtTokenProvider.extractSubject(token);
            Long userId = Long.parseLong(userIdString);
            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            throw new RuntimeException("Token not found");
        }
    }

    public void setPreferences(Long userId, UserPreferenceDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        BeanUtils.copyProperties(request, user);
    }
}
