package com.example.mountain.domain.mountain.service;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.example.mountain.domain.mountain.repository.MountainRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;

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
}
