package com.example.mountain.domain.comment.repository;

import com.example.mountain.domain.comment.dto.CommentResponse;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.mountain.domain.comment.entity.QComment.comment;
import static com.example.mountain.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<CommentResponse> findCommentsWithUsers (Long feedId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanExpression condition = comment.feed.id.eq(feedId);

        List<Tuple> tuples = queryFactory
                .select(comment, user)
                .from(comment)
                .leftJoin(user).on(comment.user.eq(user))
                .where(condition)
                .fetch();
        return tuples.stream()
                .map(tuple -> CommentResponse.of(Objects.requireNonNull(tuple.get(comment)), Objects.requireNonNull(tuple.get(user))))
                .collect(Collectors.toList());
    }
}
