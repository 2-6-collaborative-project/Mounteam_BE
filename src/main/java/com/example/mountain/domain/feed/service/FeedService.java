package com.example.mountain.domain.feed.service;

import com.example.mountain.domain.image.entity.Image;
import com.example.mountain.domain.image.repository.ImageRepository;
import com.example.mountain.domain.tag.entity.Tag;
import com.example.mountain.domain.feed.dto.request.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.response.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.dto.request.FeedUpdateRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final FeedTagRepository feedTagRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long create(Long userId, FeedCreateRequest feedCreateRequest, List<String> imgPaths){
        postBlankCheck(imgPaths);
        User user = getUser(userId);
        Feed feed = Feed.of(feedCreateRequest, user);
        createHashTag(feedCreateRequest.hashTags(), feed);


        Feed savedFeed = feedRepository.save(feed);
        List<String> imgList = new ArrayList<>();
        for (String imgUrl : imgPaths) {
            Image image = new Image(imgUrl, savedFeed);
            imageRepository.save(image);
            imgList.add(image.getImgUrl());
        }
        return savedFeed.getId();
    }

    @Transactional(readOnly = true)
    public Slice<FeedListResponse> findList(Pageable pageable, Long userId){
        Slice<FeedListResponse> feedListResponses = feedRepository.findAllFeed(pageable, userId);
        return feedListResponses;
    }
    @Transactional(readOnly = true)
    public FeedDetailResponse findFeed(Long feedId, Long userId){
        Feed feed = findFeedBy(feedId);
        return FeedDetailResponse.from(feed, userId);
    }

    @Transactional
    public String update (Long feedId, Long userId, FeedUpdateRequest feedUpdateRequest,List<String> imgPaths ) {

        User user = getUser(userId);
        Feed feed = getFeed(feedId);
        if(feed.getUser().equals(user)) {
            feed.update(feedUpdateRequest);
            if (imgPaths != null){
                List<String> imgList = new ArrayList<>();
                for (String imgUrl : imgPaths) {
                    Image image = new Image(imgUrl, feed);
                    imageRepository.save(image);
                    imgList.add(image.getImgUrl());
                }
            }
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_FEED_USER_UPDATE);
        }
        return "피드가 수정되었습니다.";
    }

    @Transactional
    public Long delete (Long feedId, Long userId) {
        User user = getUser(userId);
        Feed feed = getFeed(feedId);
        if(feed.getUser().getUserId().equals(user.getUserId())){
            feedRepository.delete(feed);
        }else {
            throw new CustomException(ErrorCode.NOT_MATCH_FEED_USER_DELETE);
        }
        return feedId;
    }

    private Feed findFeedBy(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_FEED));
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
    private void postBlankCheck(List<String> imgPaths) {
        if(imgPaths == null || imgPaths.isEmpty()){ //.isEmpty()도 되는지 확인해보기
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
    }

}
