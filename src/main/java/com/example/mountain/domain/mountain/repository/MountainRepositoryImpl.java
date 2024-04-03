package com.example.mountain.domain.mountain.repository;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import static com.example.mountain.domain.mountain.entity.QMountain.mountain;
import com.example.mountain.domain.mountain.dto.MountainScrollResponse;
import com.example.mountain.domain.mountain.entity.Mountain;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public List<MountainScrollResponse> getMountainList(String areaInterest, String high, String orderBy, Long cursor,
                                                        Pageable pageable, List<Long> teamCountByMountain) {

        // 산에 대한 팀의 개수를 키-값 쌍으로 매핑하는 맵을 생성합니다.
        Map<Long, Long> teamCountMap = IntStream.range(0, teamCountByMountain.size())
                .boxed()
                .collect(Collectors.toMap(teamCountByMountain::get, i -> i + 1L));

        // 정렬에 사용할 표현식을 가져옵니다.
        OrderSpecifier<?> orderSpecifier = getOrderByExpression(orderBy, teamCountMap);

        List<Mountain> content = jpaQueryFactory
                .select(mountain)
                .from(mountain)
                .where(ltMountainId(cursor),
                        areaInterestCondition(areaInterest),
                        highCondition(high))
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }

        // Mountain 엔티티를 MountainScrollResponse로 매핑하여 반환합니다.
        return content.stream()
                .map(mountain -> mapToMountainScrollResponse(mountain, teamCountMap))
                .collect(Collectors.toList());
    }

    // Mountain 엔티티를 MountainScrollResponse로 매핑하는 메서드입니다.
    private MountainScrollResponse mapToMountainScrollResponse(Mountain mountain, Map<Long, Long> teamCountMap) {
        MountainScrollResponse response = new MountainScrollResponse();
        response.setId(mountain.getId());
        response.setName(mountain.getName());
        response.setLocation(mountain.getLocation());
        response.setHigh(mountain.getHigh());
        // 산의 id를 이용하여 팀의 개수를 가져와서 설정합니다.
        response.setTeamCount(teamCountMap.getOrDefault(mountain.getId(), 0L).intValue());
        return response;
    }

    private BooleanExpression ltMountainId(Long startId) {
        return startId == null ? null : mountain.id.lt(startId);
    }

    // areaInterest 조건을 확인하는 메소드
    private BooleanExpression areaInterestCondition(String areaInterest) {
        return areaInterest != null ? mountain.location.contains(areaInterest) : null;
    }

    // high 조건을 확인하는 메소드
    private BooleanExpression highCondition(String high) {
        return high != null ? mountain.high.goe(high) : null;
    }

    // 정렬에 사용할 표현식을 반환하는 메서드입니다.
    private OrderSpecifier<?> getOrderByExpression(String orderBy, Map<Long, Long> teamCountMap) {
        if ("popular".equalsIgnoreCase(orderBy)) {
            // 인기순 정렬을 위한 NumberPath를 생성합니다.
            NumberPath<Long> popularityPath = Expressions.numberPath(Long.class, "popular");
            // OrderSpecifier로 변환하여 반환합니다.
            return new OrderSpecifier<>(Order.DESC, popularityPath);
        } else {
            // 이름순으로 정렬합니다.
            return mountain.name.asc();
        }
    }


}
