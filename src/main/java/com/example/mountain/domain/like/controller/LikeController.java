package com.example.mountain.domain.like.controller;

import com.example.mountain.domain.like.service.LikeService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "LIKE API", description = "피드, 등반 리뷰 좋아요 기능")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/reviews/{reviewId}/likes")
    @Operation(summary = "리뷰 좋아요")
    public GlobalResponse addReviewLike(@PathVariable Long reviewId,
                                  @AuthenticationPrincipal CustomUserDetails user){


        likeService.addReviewLike(reviewId, user.getUserId());
        return GlobalResponse.success("좋아요가 눌러졌습니다.");
    }

    @DeleteMapping("/reviews/{reviewId}/likes")
    @Operation(summary = "리뷰 좋아요 해제")
    public GlobalResponse deleteReviewLike(@PathVariable Long reviewId,
                                     @AuthenticationPrincipal CustomUserDetails user){


        likeService.deleteReviewLike(reviewId, user.getUserId());
        return GlobalResponse.success("좋아요를 해제했습니다.");
    }

}
