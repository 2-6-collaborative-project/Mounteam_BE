package com.example.mountain.domain.mountain.repository;


import com.example.mountain.domain.curation.dto.SeasonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MountainRepositoryCustom {
    Slice<SeasonResponse> findAllMountainBySeasonContaining (String season, Pageable pageable);
}
