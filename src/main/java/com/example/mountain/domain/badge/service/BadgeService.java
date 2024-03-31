package com.example.mountain.domain.badge.service;


import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.badge.repository.BadgeRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final UserRepository userRepository;

    public List<Map<String,String>> getTotalBadge(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<Badge> badges = badgeRepository.findByUser(user);
        return convertBadges(badges);
    }

    public List<Map<String, String>> convertBadges(List<Badge> badges) {
        return badges.stream()
                .map(badge -> {
                    Map<String, String> badgeMap = Map.of(
                            "badgeName", badge.getBadgeName(),
                            "badgeImg", badge.getBadgeImg()
                    );
                    return badgeMap;
                })
                .toList();
    }
}
