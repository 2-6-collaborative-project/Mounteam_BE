package com.example.mountain.domain.review.entity;

import com.example.mountain.domain.meeting.entity.Meeting;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id") // 외래 키의 컬럼명을 지정해야 합니다.
    private Meeting meeting;

}
