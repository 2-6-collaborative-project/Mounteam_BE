package com.example.mountain.domain.feed.dto.response;

import com.example.mountain.domain.user.entity.User;
import lombok.Builder;

@Builder
public record Author(String profileImageUrl,
                     String nickname,
                     Long authorId,
                     Long level) {

    public static Author from(User user){
        return Author.builder()
                .profileImageUrl(user.getProfileImage())
                .nickname(user.getNickname())
                .authorId(user.getUserId())
                .level(user.getUserLevel())
                .build();
    }
}
