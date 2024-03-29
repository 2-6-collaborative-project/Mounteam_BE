package com.example.mountain.domain.feed.controller;

import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.FeedListResponse;
import com.example.mountain.domain.feed.dto.FeedUpdateRequest;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "피드 API", description = "피드")
public class FeedController {

    private final FeedService feedService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "피드 작성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                                         @RequestBody FeedCreateRequest feedCreateRequest) {
        Long feedId = feedService.create(user.getUserId(), feedCreateRequest);
        return GlobalResponse.success(feedId);
    }

    @GetMapping
    @Operation(summary = "피드 전체 조회")
    public GlobalResponse list(){
        FeedListResponse feedList = feedService.findList();
        return GlobalResponse.success(feedList);
    }

    @GetMapping("/{feedId}")
    @Operation(summary = "피드 선택 조회")
    public GlobalResponse detail(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user){
        FeedDetailResponse feedDetailResponse = feedService.findFeed(feedId, user.getUserId());

        return GlobalResponse.success(feedDetailResponse);
    }

    @PutMapping("/{feedId}")
    @Operation(summary = "피드 수정", description = "내용만 수정가능")
    public GlobalResponse update(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user,
                         @Validated @RequestBody FeedUpdateRequest feedUpdateRequest){
        feedService.update(feedId, user.getUserId(), feedUpdateRequest);
        return GlobalResponse.success("피드가 수정되었습니다");
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "피드 삭제")
    public GlobalResponse delete(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user){
        feedService.delete(feedId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }
}
