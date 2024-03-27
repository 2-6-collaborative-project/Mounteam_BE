package com.example.mountain.domain.Tag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1759688259L;

    public static final QTag tag = new QTag("tag");

    public final com.example.mountain.global.base.QBaseEntity _super = new com.example.mountain.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final ListPath<com.example.mountain.domain.feed.entity.FeedTagMap, com.example.mountain.domain.feed.entity.QFeedTagMap> tagMap = this.<com.example.mountain.domain.feed.entity.FeedTagMap, com.example.mountain.domain.feed.entity.QFeedTagMap>createList("tagMap", com.example.mountain.domain.feed.entity.FeedTagMap.class, com.example.mountain.domain.feed.entity.QFeedTagMap.class, PathInits.DIRECT2);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

