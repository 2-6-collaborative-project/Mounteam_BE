package com.example.mountain.domain.comment.service;

import com.example.mountain.domain.comment.dto.CommentRequest;
import com.example.mountain.domain.comment.dto.CommentResponse;
import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.comment.repository.CommentRepository;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.repository.FeedRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create (Long userId, Long feedId, CommentRequest commentRequest) {
        User user = getUser(userId);
        LocalDateTime now = LocalDateTime.now();
        Feed feed = findFeedBy(feedId);

        Comment comment = Comment.create(user, commentRequest.getContent(), feed, now);
        Comment savedComment = commentRepository.save(comment);
        feed.increaseComment();
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsWithUsers(Long feedId) {
        return commentRepository.findCommentsWithUsers(feedId);
    }

    private Feed findFeedBy(Long feedId){
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_FEED));
        return feed;
    }

    private User getUser (Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }

}
