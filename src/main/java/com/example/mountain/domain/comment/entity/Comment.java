package com.example.mountain.domain.comment.entity;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public static Comment create(User user, String content, Feed feed, LocalDateTime createAt){
        return Comment.builder()
                .user(user)
                .feed(feed)
                .content(content)
                .createDate(createAt)
                .build();
    }
    public void update(String content) {
        this.content = content;
    }

}
