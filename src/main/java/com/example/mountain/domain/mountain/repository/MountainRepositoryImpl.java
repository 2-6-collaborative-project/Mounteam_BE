package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import static com.example.mountain.domain.mountain.entity.QMountain.mountain;

import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.dto.MountainSearchCondition;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<SeasonResponse> findAllMountainBySeasonContaining (String season, Pageable pageable) {

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
    public List<MountainScrollResponse> getMountainList(MountainSearchCondition searchCondition, String orderBy,
                                                        Pageable pageable) {
        List<Mountain> content = jpaQueryFactory
                .select(mountain)
                .from(mountain)
                .where(allSearch(searchCondition))
                .orderBy(orderPopulate(orderBy))
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

    private OrderSpecifier<? extends Serializable> orderPopulate (String orderBy) {
        if (Objects.equals(orderBy, "popular")) {
            return mountain.teams.size().desc();
        } else if (Objects.equals(orderBy, "asc")) {
            return mountain.name.asc();
        }
        return null;
    }

    private BooleanExpression ltMountainId (Long startId) {
        return startId == null ? null : mountain.id.gt(startId);
    }

    private BooleanExpression areaInterestCondition (String areaInterest) {
        return areaInterest != null ? mountain.location.contains(areaInterest) : null;
    }

    private BooleanExpression highCondition(Integer high) {
        if (high == null) {
            return null;
        } else if (high < 500) {
            return mountain.high.lt("500"); // 500 미만
        } else if (high >= 500 && high < 1000) {
            return mountain.high.between("500", "999"); // 500 이상 1000 미만
        } else if (high >= 1000 && high < 1500) {
            return mountain.high.between("1000", "1499"); // 1000 이상 1500 미만
        } else {
            return mountain.high.gt("1500"); // 1500 이상
        }
    }
    private BooleanExpression allSearch(MountainSearchCondition condition) {
        return highCondition(condition.getHigh())
                .and(areaInterestCondition(condition.getAreaInterest()))
                .and(ltMountainId(condition.getCursor()));
    }

}

