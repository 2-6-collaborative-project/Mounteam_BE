package com.example.mountain.domain.team.service;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.team.dto.request.TeamCreateRequest;
import com.example.mountain.domain.review.dto.request.TeamReviewRequest;
import com.example.mountain.domain.team.dto.response.TeamDetailResponse;
import com.example.mountain.domain.team.dto.response.TeamListResponse;
import com.example.mountain.domain.team.dto.request.TeamUpdateRequest;
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
import org.springframework.web.multipart.MultipartFile;

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
    public Long create(Long userId, TeamCreateRequest request, String imgUrl){
        User user = getUser(userId);
        Mountain mountain = getMountain(request);
        LocalDateTime now = LocalDateTime.now();

        List<AgeRange> ageRanges = request.getAgeRange().stream()
                .map(AgeRange::fromString)
                .collect(Collectors.toList());
        Gender gender = Gender.fromString(request.getGender());

        return teamRepository.save(Team.builder()
                .user(user)
                .createDate(now)
                .mountain(mountain)
                .title(request.getTitle())
                .content(request.getContent())
                    .gender(gender)
                    .chatLink(request.getChatLink())
                    .chatPassword(request.getChatPassword())
                    .ageRange(ageRanges)
                .departureDay(request.getDepartureDay())
                .createByMe(true)
                .teamImage(imgUrl)
                .build()).getId();
    }

    @Transactional(readOnly = true)
    public List<TeamListResponse> findList(Long userId) {
        List<Team> teams = teamRepository.findAllByOrderByCreateDateDesc();
        return TeamListResponse.from(teams, userId);
    }

    @Transactional(readOnly = true)
    public TeamDetailResponse findTeam(Long teamId, Long userId){
        Team team = findTeamBy(teamId);
        return TeamDetailResponse.from(team, userId);
    }

    @Transactional
    public TeamUpdateRequest update (Long teamId, Long userId, TeamUpdateRequest teamUpdateRequest, String imgUrl) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);

        if(team.getUser().equals(user)){
            team.update(teamUpdateRequest, imgUrl);
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_TEAM_USER_UPDATE);
        }
        return teamUpdateRequest;
    }
    @Transactional
    public TeamUpdateRequest update (Long teamId, Long userId, TeamUpdateRequest teamUpdateRequest) {
        User user = getUser(userId);
        Team team = findTeamBy(teamId);

        if(team.getUser().equals(user)){
            team.update(teamUpdateRequest);
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

    public Team findById (Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_TEAM));
    }
}
