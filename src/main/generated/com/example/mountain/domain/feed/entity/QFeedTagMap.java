package com.example.mountain.domain.feed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedTagMap is a Querydsl query type for FeedTagMap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedTagMap extends EntityPathBase<FeedTagMap> {

    private static final long serialVersionUID = -120436939L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedTagMap feedTagMap = new QFeedTagMap("feedTagMap");

    public final com.example.mountain.global.base.QBaseEntity _super = new com.example.mountain.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final QFeed feed;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.mountain.domain.Tag.entity.QTag tag;

    public QFeedTagMap(String variable) {
        this(FeedTagMap.class, forVariable(variable), INITS);
    }

    public QFeedTagMap(Path<? extends FeedTagMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedTagMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedTagMap(PathMetadata metadata, PathInits inits) {
        this(FeedTagMap.class, metadata, inits);
    }

    public QFeedTagMap(Class<? extends FeedTagMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feed = inits.isInitialized("feed") ? new QFeed(forProperty("feed"), inits.get("feed")) : null;
        this.tag = inits.isInitialized("tag") ? new com.example.mountain.domain.Tag.entity.QTag(forProperty("tag")) : null;
    }

}

