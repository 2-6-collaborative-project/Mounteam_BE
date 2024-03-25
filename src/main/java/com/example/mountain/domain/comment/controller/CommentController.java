package com.example.mountain.domain.comment.controller;

import com.example.mountain.domain.comment.dto.CommentRequest;
import com.example.mountain.domain.comment.dto.CommentResponse;
import com.example.mountain.domain.comment.dto.CommentsResponse;
import com.example.mountain.domain.comment.service.CommentService;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "CommentController", description = "피드댓글")
public class CommentController {
    private final CommentService commentService;
    private final FeedService feedService;
    private final UserService userService;

    @PostMapping("/feeds/{feedId}/comments")
    public GlobalResponse create(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long feedId, @RequestBody CommentRequest commentRequest) {
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);
        Long commentId = commentService.create(user, feedId, commentRequest);
        return GlobalResponse.success(commentId);
    }

    @GetMapping("/feeds/{feedId}/comments")
    public GlobalResponse findAllComments(@PathVariable Long feedId) {
        List<CommentResponse> comments = commentService.getCommentsWithUsers(feedId);
        return GlobalResponse.success(comments);
    }

}
