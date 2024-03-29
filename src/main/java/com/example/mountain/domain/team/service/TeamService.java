package com.example.mountain.domain.team.service;

import com.example.mountain.domain.feed.dto.FeedDetailResponse;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.mountain.service.MountainService;
import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.dto.TeamDetailResponse;
import com.example.mountain.domain.team.dto.TeamListResponse;
import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MountainRepository mountainRepository;

    @Transactional
    public Long create(Long userId, TeamCreateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Mountain mountain = mountainRepository.findByName(request.getMountain())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
        LocalDateTime now = LocalDateTime.now();

        Gender gender = null;
        for (Gender g : Gender.values()) {
            if (g.getValue().equals(request.getGender())) {
                gender = g;
                break;
            }
        }
        AgeRange ageRange = null;
        for(AgeRange a: AgeRange.values()){
            if (a.getValue().equals(request.getAgeRange())){
                ageRange = a;
                break;
            }
        }

        return teamRepository.save(Team.builder()
                .user(user)
                .createDate(now)
                .mountain(mountain)
                .title(request.getTitle())
                .content(request.getContent())
                .gender(gender)
                .chatLink(request.getChatLink())
                .chatPassword(request.getChatPassword())
                .ageRange(ageRange)
                .departureDay(request.getDepartureDay())
                .build()).getId();

    }
    @Transactional(readOnly = true)
    public TeamListResponse findList(){
        List<Team> teams = teamRepository.findAllByOrderByCreateDateDesc();
        return TeamListResponse.from(teams);
    }
    @Transactional(readOnly = true)
    public TeamDetailResponse findTeam(Long teamId, User user){
        Team team = findTeamBy(teamId);
        return TeamDetailResponse.from(team);
    }


    private Team findTeamBy(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("해당 모임이 없습니다."));
    }

}
