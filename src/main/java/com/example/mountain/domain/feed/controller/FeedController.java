package com.example.mountain.domain.feed.controller;

import com.example.mountain.domain.review.dto.response.ReviewListScrollResponse;
import com.example.mountain.domain.review.dto.response.ReviewMainScrollResponse;
import com.example.mountain.domain.review.service.ReviewService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "피드 API", description = "피드")
public class FeedController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "피드 전체 조회")
    public GlobalResponse list(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "9") int pageSize,
                               @AuthenticationPrincipal CustomUserDetails user){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        ReviewListScrollResponse reviewListPage = reviewService.findList(pageable, user.getUserId());
        return GlobalResponse.success(reviewListPage);
    }

    @GetMapping("/main")
    @Operation(summary = "피드 전체 조회(Main)")
    public GlobalResponse list(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "9") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        ReviewMainScrollResponse reviewListPage = reviewService.findListMain(pageable);
        return GlobalResponse.success(reviewListPage);
    }

}
