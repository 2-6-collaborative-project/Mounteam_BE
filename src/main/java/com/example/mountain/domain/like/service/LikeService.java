package com.example.mountain.domain.like.service;

import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.like.entity.Like;
import com.example.mountain.domain.like.repository.LikeRepository;
import com.example.mountain.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final FeedService feedService;
    private final LikeRepository likeRepository;

    @Transactional
    public void addLike (Long feedId, User user) {
        Long userId = user.getUserId();
        Feed feed = feedService.findFeedBy(feedId);

        Like feedLike = likeRepository.findUserId(userId, feedId);
        if (feedLike == null){
            Like like = Like.builder()
                .feed(feed)
                .user(user)
                .build();
            likeRepository.save(like);
            feed.increaseLike();
        }
    }

    @Transactional
    public void deleteLike (Long feedId, User user) {
        Long userId = user.getUserId();
        Feed feed = feedService.findFeedBy(feedId);

        Like feedLike = likeRepository.findUserId(userId, feedId);
        if (feedLike != null){
            likeRepository.deleteFeed_Id(feedId);
            feed.decreaseLike();
        }
    }


}
