package com.example.mountain.domain.feed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = -717763085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeed feed = new QFeed("feed");

    public final com.example.mountain.global.base.QBaseEntity _super = new com.example.mountain.global.base.QBaseEntity(this);

    public final NumberPath<Integer> commentCnt = createNumber("commentCnt", Integer.class);

    public final ListPath<com.example.mountain.domain.comment.entity.Comment, com.example.mountain.domain.comment.entity.QComment> comments = this.<com.example.mountain.domain.comment.entity.Comment, com.example.mountain.domain.comment.entity.QComment>createList("comments", com.example.mountain.domain.comment.entity.Comment.class, com.example.mountain.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<FeedTagMap, QFeedTagMap> hashTag = this.<FeedTagMap, QFeedTagMap>createList("hashTag", FeedTagMap.class, QFeedTagMap.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.mountain.domain.image.entity.Image, com.example.mountain.domain.image.entity.QImage> images = this.<com.example.mountain.domain.image.entity.Image, com.example.mountain.domain.image.entity.QImage>createList("images", com.example.mountain.domain.image.entity.Image.class, com.example.mountain.domain.image.entity.QImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.mountain.domain.user.entity.QUser user;

    public QFeed(String variable) {
        this(Feed.class, forVariable(variable), INITS);
    }

    public QFeed(Path<? extends Feed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeed(PathMetadata metadata, PathInits inits) {
        this(Feed.class, metadata, inits);
    }

    public QFeed(Class<? extends Feed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.mountain.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

