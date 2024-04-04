package com.example.mountain.domain.team.repository;

import com.example.mountain.domain.team.dto.response.TeamListResponse;
import com.example.mountain.domain.team.dto.response.TeamListScrollResponse;
import com.example.mountain.domain.team.entity.Team;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.mountain.domain.team.entity.QTeam.team;

@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public TeamListScrollResponse getMyTeams(Long userId, Pageable pageable, Long cursorId) {

        List<Team> content = jpaQueryFactory
                .select(team)
                .from(team)
                .where(team.user.userId.eq(userId),
                        ltTeamId(cursorId))
                .orderBy(team.id.desc())
                .limit(pageable.getPageSize() +1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<TeamListResponse> teamListResponses = TeamListResponse.from(content, userId);
        return new TeamListScrollResponse(teamListResponses, hasNext);
    }

    private BooleanExpression ltTeamId(Long startId) {
        return startId == null ? null : team.id.lt(startId);
    }

}
