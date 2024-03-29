package com.example.mountain.domain.team.service;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.dto.TeamDetailResponse;
import com.example.mountain.domain.team.dto.TeamListResponse;
import com.example.mountain.domain.team.dto.TeamUpdateRequest;
import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MountainRepository mountainRepository;

    @Transactional
    public Long create(Long userId, TeamCreateRequest request){
        User user = getUser(userId);
        Mountain mountain = getMountain(request);
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
    public TeamDetailResponse findTeam(Long teamId, Long userId){
        Team team = findTeamBy(teamId);
        return TeamDetailResponse.from(team);
    }

    @Transactional
    public TeamUpdateRequest update ( Long teamId, Long userId, TeamUpdateRequest teamUpdateRequest) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);

        if(team.getUser().equals(user)){
            team.update(teamUpdateRequest);
        }
        return teamUpdateRequest;
    }
    @Transactional
    public Long delete (Long teamId, Long userId) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);
        if(team.getUser().equals(user)){
            teamRepository.delete(team);
        }
        return teamId;
    }

    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }

    private Mountain getMountain (TeamCreateRequest request) {
        Mountain mountain = mountainRepository.findByName(request.getMountain())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
        return mountain;
    }

    private Team findTeamBy(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TEAM));
    }

}