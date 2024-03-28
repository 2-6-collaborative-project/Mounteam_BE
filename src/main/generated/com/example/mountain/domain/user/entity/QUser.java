package com.example.mountain.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 32833229L;

    public static final QUser user = new QUser("user");

    public final com.example.mountain.global.base.QBaseEntity _super = new com.example.mountain.global.base.QBaseEntity(this);

    public final StringPath ageRange = createString("ageRange");

    public final StringPath areaInterest = createString("areaInterest");

    public final ListPath<com.example.mountain.domain.badge.entity.Badge, com.example.mountain.domain.badge.entity.QBadge> badges = this.<com.example.mountain.domain.badge.entity.Badge, com.example.mountain.domain.badge.entity.QBadge>createList("badges", com.example.mountain.domain.badge.entity.Badge.class, com.example.mountain.domain.badge.entity.QBadge.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath gender = createString("gender");

    public final StringPath introduction = createString("introduction");

    public final StringPath locationAgree = createString("locationAgree");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath nickname = createString("nickname");

    public final EnumPath<com.example.mountain.oauth.OauthProvider> oauthProvider = createEnum("oauthProvider", com.example.mountain.oauth.OauthProvider.class);

    public final StringPath password = createString("password");

    public final StringPath privacyAgree = createString("privacyAgree");

    public final StringPath profileImage = createString("profileImage");

    public final SetPath<Role, EnumPath<Role>> roles = this.<Role, EnumPath<Role>>createSet("roles", Role.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath userAccount = createString("userAccount");

    public final ListPath<com.example.mountain.domain.feed.entity.Feed, com.example.mountain.domain.feed.entity.QFeed> userFeeds = this.<com.example.mountain.domain.feed.entity.Feed, com.example.mountain.domain.feed.entity.QFeed>createList("userFeeds", com.example.mountain.domain.feed.entity.Feed.class, com.example.mountain.domain.feed.entity.QFeed.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userLevel = createString("userLevel");

    public final ListPath<com.example.mountain.domain.userMeeting.entity.UserMeeting, com.example.mountain.domain.userMeeting.entity.QUserMeeting> userMeetings = this.<com.example.mountain.domain.userMeeting.entity.UserMeeting, com.example.mountain.domain.userMeeting.entity.QUserMeeting>createList("userMeetings", com.example.mountain.domain.userMeeting.entity.UserMeeting.class, com.example.mountain.domain.userMeeting.entity.QUserMeeting.class, PathInits.DIRECT2);

    public final ListPath<com.example.mountain.domain.userReview.entity.UserReview, com.example.mountain.domain.userReview.entity.QUserReview> userReviews = this.<com.example.mountain.domain.userReview.entity.UserReview, com.example.mountain.domain.userReview.entity.QUserReview>createList("userReviews", com.example.mountain.domain.userReview.entity.UserReview.class, com.example.mountain.domain.userReview.entity.QUserReview.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

