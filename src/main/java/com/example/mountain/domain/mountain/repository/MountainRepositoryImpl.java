package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.curation.dto.SeasonResponse;

import static com.example.mountain.domain.mountain.entity.QMountain.mountain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom{

    private final EntityManager em;

    @Override
    public Slice<SeasonResponse> findAllMountainBySeasonContaining(String season, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 시즌을 포함하는 산을 조회하는 쿼리
        List<SeasonResponse> springMountains = queryFactory.selectFrom(mountain)
                .where(mountain.season.like("%" + season + "%")) // 주어진 시즌을 포함하는 season 검색
                .orderBy(mountain.name.asc()) // 이름을 기준으로 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(m -> SeasonResponse.from(m, m.getName())) // SeasonResponse로 매핑
                .collect(Collectors.toList());

        // 총 개수를 가져오기 위한 쿼리
        long total = queryFactory.selectFrom(mountain)
                .where(mountain.season.like("%" + season + "%")) // 주어진 시즌을 포함하는 season 검색
                .fetchCount();

        return new PageImpl<>(springMountains, pageable, total);
    }
}
