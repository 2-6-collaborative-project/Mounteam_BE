package com.example.mountain.domain.mountain.repository;


import com.example.mountain.domain.curation.dto.SeasonResponse;
import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.dto.MountainSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MountainRepositoryCustom {
    Slice<SeasonResponse> findAllMountainBySeasonContaining (String season, Pageable pageable);
    List<MountainScrollResponse> getMountainList(String areaInterest, Integer high, String orderBy, Long cursor, Pageable pageable);
}
