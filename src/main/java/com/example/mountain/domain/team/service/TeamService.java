package com.example.mountain.domain.team.service;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.team.dto.request.TeamCreateRequest;
import com.example.mountain.domain.team.dto.response.TeamDetailResponse;
import com.example.mountain.domain.team.dto.request.TeamUpdateRequest;
import com.example.mountain.domain.team.dto.response.TeamListScrollResponse;
import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MountainRepository mountainRepository;

    @Transactional
    public Long create(Long userId, TeamCreateRequest request){
        User user = getUser(userId);
        Mountain mountain = getMountain(request.getMountain());
        LocalDateTime now = LocalDateTime.now();

        List<AgeRange> ageRanges = request.getAgeRange().stream()
                .map(AgeRange::fromString)
                .collect(Collectors.toList());
        Gender gender = Gender.fromString(request.getGender());


        return teamRepository.save(Team.builder()
                .user(user)
                .createdAt(now)
                .mountain(mountain)
                .title(request.getTitle())
                .content(request.getContent())
                .gender(gender)
                .chatLink(request.getChatLink())
                .chatPassword(request.getChatPassword())
                .ageRange(ageRanges)
                .departureDay(request.getDepartureDay())
                .createByMe(true)
                .build()).getId();
    }

    @Transactional(readOnly = true)
    public TeamListScrollResponse findPagedTeams(Long cusor, Pageable pageable) {
        return teamRepository.getTeamList(cusor,pageable);
    }

    @Transactional(readOnly = true)
    public TeamDetailResponse findTeam(Long teamId, Long userId){
        Team team = findTeamBy(teamId);
        return TeamDetailResponse.from(team, userId);
    }

    @Transactional
    public TeamUpdateRequest update ( Long teamId, Long userId, TeamUpdateRequest teamUpdateRequest) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);
        Mountain mountain = getMountain(teamUpdateRequest.getMountain());
        if(team.getUser().equals(user)){
            team.update(teamUpdateRequest, mountain);
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_TEAM_USER_UPDATE);
        }
        return teamUpdateRequest;
    }
    @Transactional
    public Long delete (Long teamId, Long userId) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);
        if(team.getUser().equals(user)){
            teamRepository.delete(team);
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_TEAM_USER_DELETE);
        }
        return teamId;
    }

    @Transactional(readOnly = true)
    public boolean valid (Long teamId, Long userId){
        User user = getUser(userId);
        Team team = findTeamBy(teamId);


        if (!team.getGender().equals(Gender.all)) {
            Gender userGender = Gender.fromString(user.getGender());
            if(team.getGender() != userGender)
                throw new CustomException(ErrorCode.NOT_VALID_GENDER);
        }

        AgeRange userAge= AgeRange.fromString(user.getAgeRange());
        if(!team.getAgeRange().contains(userAge)){
            throw new CustomException(ErrorCode.NOT_VALID_AGE_RANGE);
        }
        return true;
    }

    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }

    private Mountain getMountain (String mountainName) {
        Mountain mountain = mountainRepository.findByName(mountainName)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
        return mountain;
    }

    private Team findTeamBy(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TEAM));
    }

    public Team findById (Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_TEAM));
    }
}
