package com.example.mountain.domain.userReview.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReview is a Querydsl query type for UserReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReview extends EntityPathBase<UserReview> {

    private static final long serialVersionUID = 1379453501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReview userReview = new QUserReview("userReview");

    public final com.example.mountain.global.base.QBaseEntity _super = new com.example.mountain.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.mountain.domain.review.entity.QReview review;

    public final com.example.mountain.domain.user.entity.QUser user;

    public QUserReview(String variable) {
        this(UserReview.class, forVariable(variable), INITS);
    }

    public QUserReview(Path<? extends UserReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReview(PathMetadata metadata, PathInits inits) {
        this(UserReview.class, metadata, inits);
    }

    public QUserReview(Class<? extends UserReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new com.example.mountain.domain.review.entity.QReview(forProperty("review"), inits.get("review")) : null;
        this.user = inits.isInitialized("user") ? new com.example.mountain.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

