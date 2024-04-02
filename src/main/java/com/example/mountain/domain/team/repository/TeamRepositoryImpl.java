package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListResponse;
import com.example.mountain.domain.team.entity.Team;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mountain.domain.team.entity.QTeam.team;

@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<TeamListResponse> getMyTeams(Long userId, Pageable pageable, Long cursorId) {

        List<Team> content = jpaQueryFactory
                .select(team)
                .from(team)
                .where(team.user.userId.eq(userId),
                        gtTeamId(cursorId))
                .orderBy(team.id.desc())
                .limit(pageable.getPageSize() +1 )
                .fetch();

        List<TeamListResponse> teamListResponses = TeamListResponse.from(content);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(teamListResponses, pageable, hasNext);
    }
    private BooleanExpression gtTeamId(Long cursorId) {
        return cursorId == null ? null : team.id.gt(cursorId);
    }
}
