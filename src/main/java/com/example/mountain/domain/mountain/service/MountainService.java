package com.example.mountain.domain.mountain.service;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.domain.team.repository.TeamRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Mountain findByName(String mountainName) {
        return mountainRepository.findByName(mountainName)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
    }

    @Transactional(readOnly = true)
    public Slice<SeasonResponse> findSeasonList (Pageable pageable, String season) {
        Slice<SeasonResponse> seasonResponses = mountainRepository.findAllMountainBySeasonContaining(season, pageable);
        return seasonResponses;
    }

    @Transactional(readOnly = true)
    public List<String> autoComplete() {
        return mountainRepository.findAllNames();
    }


    @Transactional(readOnly = true)
    public List<MountainScrollResponse> getMountainList(String areaInterest, String high, String orderBy, Long cursor, Pageable pageable) {
        List<Long> teamCountByMountain = teamRepository.countTeamsByMountain();
        return mountainRepository.getMountainList(areaInterest, high, orderBy, cursor, pageable, teamCountByMountain);
    }
}
