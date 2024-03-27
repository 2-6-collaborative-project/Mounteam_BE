package com.example.mountain.domain.comment.service;

import com.example.mountain.domain.comment.dto.CommentRequest;
import com.example.mountain.domain.comment.dto.CommentResponse;
import com.example.mountain.domain.comment.entity.Comment;
import com.example.mountain.domain.comment.repository.CommentRepository;
import com.example.mountain.domain.feed.entity.Feed;
import com.example.mountain.domain.feed.repository.FeedRepository;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
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
    public Long create (User user, Long feedId, CommentRequest commentRequest) {
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
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }

}
