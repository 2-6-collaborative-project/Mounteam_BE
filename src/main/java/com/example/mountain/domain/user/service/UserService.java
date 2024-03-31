package com.example.mountain.domain.user.service;


import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.badge.repository.BadgeRepository;
import com.example.mountain.domain.badge.service.BadgeService;
import com.example.mountain.domain.user.dto.UserMyProfileDto;
import com.example.mountain.domain.user.dto.UserPreferenceDto;
import com.example.mountain.domain.user.dto.UserRequestDto;
import com.example.mountain.domain.user.dto.UserUpdateProfileDto;
import com.example.mountain.domain.user.entity.Role;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.oauth.jwt.AuthTokens;
import com.example.mountain.oauth.jwt.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthTokensGenerator authTokensGenerator;
    private final BadgeService badgeService;

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

    //삭제 예정
    public AuthTokens loginUser(UserRequestDto.LoginUserDto requestDto) {
        User user = userRepository.findByUserAccount(requestDto.getAccount())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        boolean isNewUser = user.getPrivacyAgree() == null ? true : false;
        return authTokensGenerator.generate(user.getUserId(), isNewUser);
    }

    //삭제 예정
    public Long generateID() {
        long randomNumber = ThreadLocalRandom.current().nextLong(1, 2001);
        long randomId = Math.abs(randomNumber);

        if (userRepository.findById(randomId).isPresent() ) {
            return generateID();
        }
        return randomId;
    }

    public void setPreferences(Long userId, UserPreferenceDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        BeanUtils.copyProperties(request, user);
    }

    public UserMyProfileDto getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<Badge> badges = badgeRepository.findTop3ByUserOrderByCreateDateDesc(user);
        List<Map<String, String>> latestBadges = badgeService.convertBadges(badges);
        Long totalBadgeCount = badgeRepository.countByUser(user);
        return new UserMyProfileDto(user, latestBadges, totalBadgeCount);
    }

    public void updateProfile(Long userId, UserUpdateProfileDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));


    }
}
