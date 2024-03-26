package com.example.mountain.domain.like.controller;

import com.example.mountain.domain.like.dto.LikeRequest;
import com.example.mountain.domain.like.service.LikeService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
@Tag(name = "피드 LIKE API", description = "피드의 하트")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{feedId}/like")
    @Operation(summary = "피드 좋아요")
    public GlobalResponse<LikeRequest> addLike(@PathVariable Long feedId,
                                               @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);

        likeService.addLike(feedId, user);
        return GlobalResponse.success(new LikeRequest(user.getUserId(), feedId));
    }

    @DeleteMapping("/{feedId}/like")
    @Operation(summary = "피드 좋아요 해제")
    public GlobalResponse<LikeRequest> deleteLike(@PathVariable Long feedId,
                                               @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);

        likeService.deleteLike(feedId, user);
        return GlobalResponse.success(new LikeRequest(user.getUserId(), feedId));
    }

}
