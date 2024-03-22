package com.example.mountain.domain.feed.service;

import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.FeedListResponse;
import com.example.mountain.domain.feed.dto.FeedUpdateRequest;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.repository.FeedRepository;
import com.example.mountain.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional
    public Long create(User user, FeedCreateRequest feedCreateRequest){
        LocalDateTime now = LocalDateTime.now();
        Feed feed = Feed.of(feedCreateRequest, user, now);
        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }
    @Transactional(readOnly = true)
    public FeedListResponse findList(){
        List<Feed> feeds = feedRepository.findAllByOrderByCreateDateDesc();
        return FeedListResponse.from(feeds);
    }
    @Transactional(readOnly = true)
    public FeedDetailResponse findFeed(Long feedId, User user){
        Feed feed = findFeedBy(feedId);
        return FeedDetailResponse.from(feed);
    }

    @Transactional
    public String update (Long feedId, User user, FeedUpdateRequest feedUpdateRequest) {
        String message = "fail";
        Feed feed = feedRepository.findById(feedId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(feed.getUser().equals(user)){
            feed.update(feedUpdateRequest);
            message = "success";
        }
        return message;
    }
    @Transactional
    public String delete (Long feedId, User user) {
        String message = "fail";
        Feed feed = feedRepository.findById(feedId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(feed.getUser().equals(user)){
            feedRepository.delete(feed);
            message = "success";
        }
        return message;
    }

    private Feed findFeedBy(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
    }
}
