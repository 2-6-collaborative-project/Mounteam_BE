package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import static com.example.mountain.domain.mountain.entity.QMountain.mountain;
import static com.example.mountain.domain.team.entity.QTeam.team;

import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Slice<SeasonResponse> findAllMountainBySeasonContaining(String season, Pageable pageable) {

        // 시즌을 포함하는 산을 조회하는 쿼리
        List<SeasonResponse> springMountains = jpaQueryFactory.selectFrom(mountain)
                .where(mountain.season.like("%" + season + "%")) // 주어진 시즌을 포함하는 season 검색
                .orderBy(mountain.name.asc()) // 이름을 기준으로 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(m -> SeasonResponse.from(m, m.getName())) // SeasonResponse로 매핑
                .collect(Collectors.toList());

        // 총 개수를 가져오기 위한 쿼리
        long total = jpaQueryFactory.selectFrom(mountain)
                .where(mountain.season.like("%" + season + "%")) // 주어진 시즌을 포함하는 season 검색
                .fetchCount();

        return new PageImpl<>(springMountains, pageable, total);
    }

    @Override
    public List<MountainScrollResponse> getMountainList(String areaInterest, String high, String orderBy,
                                                        Long cursor, Pageable pageable) {

        List<Mountain> content = jpaQueryFactory
                .select(Projections.constructor(
                        Mountain.class,
                        mountain.id,
                        mountain.name,
                        mountain.explanation,
                        mountain.img,
                        mountain.high,
                        mountain.location,
                        mountain.difficulty,
                        mountain.season,
                        mountain.theme,
                        mountain.longtitue,
                        mountain.lattitue,
                        mountain.teams.size(),
                        mountain.reviews.size()
                        ))
                .from(mountain)
                .leftJoin(mountain.teams, team)
                .on(mountain.id.eq(team.mountain.id))
                .where(ltMountainId(cursor),
                        areaInterestCondition(areaInterest),
                        highCondition(high))
                .groupBy(mountain.id, mountain.name, mountain.location, mountain.high, mountain.longtitue, mountain.lattitue, mountain.img)
                .orderBy(orderBy.equalsIgnoreCase("popular") ? mountain.teams.size().desc() : mountain.name.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<MountainScrollResponse> responseList = MountainScrollResponse.from(content, hasNext);
        return responseList;
    }


    private BooleanExpression ltMountainId(Long startId) {
        return startId == null ? null : mountain.id.lt(startId);
    }

    private BooleanExpression areaInterestCondition(String areaInterest) {
        return areaInterest != null ? mountain.location.contains(areaInterest) : null;
    }

    private BooleanExpression highCondition(String high) {
        if (high == null || high.equalsIgnoreCase("all")) {
            return null;
        } else if (high.equals("500")) {
            return mountain.high.loe("500"); // 500 미만
        } else if (high.equals("500-1000")) {
            return mountain.high.between("500", "999"); // 500 이상 1000 미만
        } else if (high.equals("1000-1500")) {
            return mountain.high.between("1000", "1499"); // 1000 이상 1500 미만
        } else if (high.equals("1500")) {
            return mountain.high.goe("1500"); // 1500 이상
        } else {
            throw new IllegalArgumentException("Invalid high value: " + high);
        }
    }

}
