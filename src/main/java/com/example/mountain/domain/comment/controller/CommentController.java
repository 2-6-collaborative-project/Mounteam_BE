package com.example.mountain.domain.comment.controller;

import com.example.mountain.domain.comment.dto.request.CommentRequest;
import com.example.mountain.domain.comment.dto.response.CommentResponse;
import com.example.mountain.domain.comment.service.CommentService;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "피드 댓글 API", description = "피드 댓글")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{feedId}/comments")
    @Operation(summary = "피드댓글 작성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                 @PathVariable Long feedId,
                                 @RequestBody CommentRequest commentRequest) {

        Long commentId = commentService.create(user.getUserId(), feedId, commentRequest);
        return GlobalResponse.success(commentId);
    }

    @GetMapping("/{feedId}/comments")
    @Operation(summary = "피드 댓글 전체 조회")
    public GlobalResponse findAllComments(@PathVariable Long feedId) {
        List<CommentResponse> comments = commentService.getCommentsWithUsers(feedId);
        return GlobalResponse.success(comments);
    }

}
