package com.example.mountain.domain.mountain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMountain is a Querydsl query type for Mountain
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMountain extends EntityPathBase<Mountain> {

    private static final long serialVersionUID = 577538641L;

    public static final QMountain mountain = new QMountain("mountain");

    public final StringPath elapsedTime = createString("elapsedTime");

    public final StringPath high = createString("high");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final StringPath level = createString("level");

    public final StringPath location = createString("location");

    public final ListPath<com.example.mountain.domain.meeting.entity.Meeting, com.example.mountain.domain.meeting.entity.QMeeting> meetings = this.<com.example.mountain.domain.meeting.entity.Meeting, com.example.mountain.domain.meeting.entity.QMeeting>createList("meetings", com.example.mountain.domain.meeting.entity.Meeting.class, com.example.mountain.domain.meeting.entity.QMeeting.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> numberCourse = createNumber("numberCourse", Integer.class);

    public QMountain(String variable) {
        super(Mountain.class, forVariable(variable));
    }

    public QMountain(Path<? extends Mountain> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMountain(PathMetadata metadata) {
        super(Mountain.class, metadata);
    }

}

