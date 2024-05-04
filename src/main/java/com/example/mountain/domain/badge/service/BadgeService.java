package com.example.mountain.domain.badge.service;


import com.example.mountain.domain.badge.entity.Badge;
import com.example.mountain.domain.badge.repository.BadgeRepository;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.aws.S3Service;
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
    private final TeamRepository teamRepository;
    private final MountainRepository mountainRepository;
    private final S3Service s3Service;

    public List<Map<String,String>> getTotalBadge(Long userId) {
        User user = getUser(userId);

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

    public void receiveBadge(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TEAM));

        Long mountainId = team.getMountain().getId();
        String mountainName = mountainRepository.findByName(mountainId);

        String dirName = "badge";
        String badgeImgUrl = s3Service.getBadgeImgUrl(dirName,mountainName);

        User user = getUser(userId);
        Badge badge = Badge.createBadge(mountainName, badgeImgUrl, user);
        badgeRepository.save(badge);
    }

    private User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }
}
