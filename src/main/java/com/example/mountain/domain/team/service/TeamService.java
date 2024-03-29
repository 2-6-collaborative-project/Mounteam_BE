package com.example.mountain.domain.team.service;

import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.service.MountainService;
import com.example.mountain.domain.team.dto.TeamCreateRequest;
import com.example.mountain.domain.team.entity.AgeRange;
import com.example.mountain.domain.team.entity.Gender;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final MountainService mountainService;

    @Transactional
    public Long create(User user, TeamCreateRequest request){

        Mountain mountain = mountainService.findByName(request.getMountain());
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
}
