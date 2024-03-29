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
@RequestMapping("/api/feeds")
@Tag(name = "피드 LIKE API", description = "피드의 하트")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{feedId}/likes")
    @Operation(summary = "피드 좋아요")
    public GlobalResponse addLike(@PathVariable Long feedId,
                                  @AuthenticationPrincipal CustomUserDetails user){


        likeService.addLike(feedId, user.getUserId());
        return GlobalResponse.success("좋아요가 눌러졌습니다.");
    }

    @DeleteMapping("/{feedId}/likes")
    @Operation(summary = "피드 좋아요 해제")
    public GlobalResponse deleteLike(@PathVariable Long feedId,
                                     @AuthenticationPrincipal CustomUserDetails user){


        likeService.deleteLike(feedId, user.getUserId());
        return GlobalResponse.success("좋아요를 해제했습니다.");
    }

}
