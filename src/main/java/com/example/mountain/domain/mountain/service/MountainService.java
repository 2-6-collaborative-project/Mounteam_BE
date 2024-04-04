package com.example.mountain.domain.mountain.service;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import com.example.mountain.domain.mountain.dto.MountainDetailResponse;
import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.dto.MountainSearchCondition;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    public List<MountainScrollResponse> getMountainList(String areaInterest, Integer high, String orderBy,
                                                        Long cursor, Pageable pageable) {
        return mountainRepository.getMountainList(MountainSearchCondition.of(cursor,high,areaInterest) ,orderBy, pageable);
    }

    @Transactional(readOnly = true)
    public MountainDetailResponse getMountainDetail (Long mountainId) {
        Mountain mountain = mountainRepository.findById(mountainId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MOUNTAIN));
        return MountainDetailResponse.from(mountain);

    }
}
