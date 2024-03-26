package com.example.mountain.domain.feed.service;

import com.example.mountain.domain.Tag.Service.TagService;
import com.example.mountain.domain.Tag.entity.Tag;
import com.example.mountain.domain.feed.dto.request.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.response.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.dto.request.FeedUpdateRequest;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.entity.FeedTagMap;
import com.example.mountain.domain.feed.repository.FeedRepository;
import com.example.mountain.domain.feed.repository.FeedTagRepository;
import com.example.mountain.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final TagService tagService;
    private final FeedTagRepository feedTagRepository;

    @Transactional
    public Long create(User user, FeedCreateRequest feedCreateRequest){
        LocalDateTime now = LocalDateTime.now();
        Feed feed = Feed.of(feedCreateRequest, user, now);
        crateHashTag(feedCreateRequest.hashTags(), feed);


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
    @Transactional(readOnly = true)
    public Optional<Feed> findFeedId(Long feedId){
        Optional<Feed> feed = feedRepository.findById(feedId);
        return feed;
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

        if(feed.getUser().getUserId().equals(user.getUserId())){
            feedRepository.delete(feed);
            message = "success";
        }
        return message;
    }

    public Feed findFeedBy(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
    }

    private void crateHashTag(List<String> hashtags, Feed feed) {
        List<Tag> savedHashTags = tagService.saveTag(hashtags);
        List<FeedTagMap> feedTagMaps = savedHashTags.stream()
                .map(tag -> FeedTagMap.createPostHashtag(tag, feed))
                .collect(Collectors.toList());
        feedTagRepository.saveAll(feedTagMaps);
    }



}
