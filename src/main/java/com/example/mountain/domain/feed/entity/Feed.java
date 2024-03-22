package com.example.mountain.domain.feed.entity;

import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.FeedUpdateRequest;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EnableJpaAuditing
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    private String content;
    private Integer likeCnt;
//    private List<Image> imgUrls = new ArrayList<>();
    private String tag;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static Feed of(FeedCreateRequest feedCreateRequest, User user, LocalDateTime now){
        return Feed.builder()
                .content(feedCreateRequest.getContent())
                .tag(feedCreateRequest.getTag())
                .createDate(now)
                .user(user)
                .build();
    }
    public void update(FeedUpdateRequest updateRequest){
        this.content = updateRequest.getContent();
        this.tag = updateRequest.getTag();
    }
}
