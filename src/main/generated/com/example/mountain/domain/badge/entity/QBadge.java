package com.example.mountain.domain.badge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBadge is a Querydsl query type for Badge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBadge extends EntityPathBase<Badge> {

    private static final long serialVersionUID = 819179773L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBadge badge = new QBadge("badge");

    public final StringPath badgeImg = createString("badgeImg");

    public final StringPath badgeName = createString("badgeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.mountain.domain.user.entity.QUser user;

    public QBadge(String variable) {
        this(Badge.class, forVariable(variable), INITS);
    }

    public QBadge(Path<? extends Badge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBadge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBadge(PathMetadata metadata, PathInits inits) {
        this(Badge.class, metadata, inits);
    }

    public QBadge(Class<? extends Badge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.mountain.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

