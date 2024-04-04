package com.example.mountain.domain.comment.controller;

import com.example.mountain.domain.comment.dto.request.CommentRequest;
import com.example.mountain.domain.comment.dto.response.ReviewCommentResponse;
import com.example.mountain.domain.comment.service.CommentService;

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
@RequestMapping("/api")
@Tag(name = "댓글 API", description = "피드, 등반 리뷰 댓글")
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/reviews/{reviewId}/comments")
    @Operation(summary = "등반 리뷰 댓글 작성")
    public GlobalResponse createReviewComment(@AuthenticationPrincipal CustomUserDetails user,
                                 @PathVariable Long reviewId,
                                 @RequestBody CommentRequest commentRequest) {

        Long commentId = commentService.createReviewComment(user.getUserId(), reviewId, commentRequest);
        return GlobalResponse.success(commentId);
    }

    @GetMapping("/reviews/{reviewId}/comments")
    @Operation(summary = "등반 리뷰 댓글 전체 조회")
    public GlobalResponse findAllReviewComments(@PathVariable Long reviewId) {
        List<ReviewCommentResponse> comments = commentService.getReviewCommentsWithUsers(reviewId);
        return GlobalResponse.success(comments);
    }

    @PostMapping("/team-reviews/{reviewId}/comments")
    @Operation(summary = "모임(팀) 리뷰 댓글 작성")
    public GlobalResponse createTeamReviewComment(@AuthenticationPrincipal CustomUserDetails user,
                                              @PathVariable Long reviewId,
                                              @RequestBody CommentRequest commentRequest) {

        Long commentId = commentService.createReviewComment(user.getUserId(), reviewId, commentRequest);
        return GlobalResponse.success(commentId);
    }

    @GetMapping("/team-reviews/{reviewId}/comments")
    @Operation(summary = "모임(팀) 리뷰 댓글 전체 조회")
    public GlobalResponse findAllTeamReviewComments(@PathVariable Long reviewId) {
        List<ReviewCommentResponse> comments = commentService.getReviewCommentsWithUsers(reviewId);
        return GlobalResponse.success(comments);
    }
}
