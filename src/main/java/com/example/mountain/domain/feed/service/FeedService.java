package com.example.mountain.domain.feed.service;

import com.example.mountain.domain.tag.entity.Tag;
import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.FeedListResponse;
import com.example.mountain.domain.feed.dto.FeedUpdateRequest;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import com.example.mountain.domain.feed.repository.FeedRepository;
import com.example.mountain.domain.feed.repository.FeedTagRepository;
import com.example.mountain.domain.tag.repostiory.TagRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final FeedTagRepository feedTagRepository;

    @Transactional
    public Long create(Long userId, FeedCreateRequest feedCreateRequest){
        User user = getUser(userId);
        LocalDateTime now = LocalDateTime.now();
        Feed feed = Feed.of(feedCreateRequest, user, now);
        createHashTag(feedCreateRequest.hashTags(), feed);


        Feed savedFeed = feedRepository.save(feed);
        return savedFeed.getId();
    }
    @Transactional(readOnly = true)
    public FeedListResponse findList(){
        List<Feed> feeds = feedRepository.findAllByOrderByCreateDateDesc();
        return FeedListResponse.from(feeds);
    }
    @Transactional(readOnly = true)
    public FeedDetailResponse findFeed(Long feedId, Long userId){
        Feed feed = findFeedBy(feedId);
        return FeedDetailResponse.from(feed);
    }

    @Transactional
    public FeedUpdateRequest update (Long feedId, Long userId, FeedUpdateRequest feedUpdateRequest) {
        User user = getUser(userId);
        Feed feed = getFeed(feedId);
        if(feed.getUser().equals(user)){
            feed.update(feedUpdateRequest);
        }
        return feedUpdateRequest;
    }

    @Transactional
    public Long delete (Long feedId, Long userId) {
        User user = getUser(userId);
        Feed feed = getFeed(feedId);
        if(feed.getUser().getUserId().equals(user.getUserId())){
            feedRepository.delete(feed);
        }
        return feedId;
    }

    private Feed findFeedBy(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
    }

    private void createHashTag(List<String> hashtags, Feed feed) {
        List<FeedTagMap> feedTagMaps = hashtags.stream()
                .map(name -> saveTag(name))
                .map(tag -> FeedTagMap.createPostHashtag(tag, feed))
                .collect(Collectors.toList());
        feedTagRepository.saveAll(feedTagMaps);
    }
    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }
    private Feed getFeed (Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_FEED));
        return feed;
    }
    private Tag saveTag(String name) {
        return tagRepository
                .findByName(name)
                .orElseGet(() -> tagRepository.save(Tag.builder()
                        .name(name)
                        .build()));
    }
}
