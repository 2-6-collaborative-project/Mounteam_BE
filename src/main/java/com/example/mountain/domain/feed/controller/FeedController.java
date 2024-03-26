package com.example.mountain.domain.feed.controller;

import com.example.mountain.domain.feed.dto.request.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.response.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.dto.request.FeedUpdateRequest;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.oauth.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
@Tag(name = "피드 API", description = "피드")
public class FeedController {

    private final FeedService feedService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @Operation(summary = "피드 작성")
    public ResponseEntity<GlobalResponse<String>> create(@RequestHeader("Authorization") String authorizationHeader,
                                                         @RequestBody FeedCreateRequest feedCreateRequest) {
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);
        feedService.create(user, feedCreateRequest);
        return ResponseEntity.ok(GlobalResponse.success());
    }

    @GetMapping
    @Operation(summary = "피드 전체 조회")
    public GlobalResponse<Page<FeedListResponse>> list(User user,
                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "9") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        Page<FeedListResponse> feedListPage = feedService.findList(pageable);
        return GlobalResponse.success(feedListPage);
    }

    @GetMapping("/{feedId}")
    @Operation(summary = "피드 선택 조회")
    public ResponseEntity<FeedDetailResponse> detail(@PathVariable Long feedId, User user){
        return ResponseEntity.ok(feedService.findFeed(feedId, user));
    }

    @PutMapping("/{feedId}")
    @Operation(summary = "피드 수정", description = "내용만 수정가능")
    public String update(@PathVariable Long feedId, @RequestHeader("Authorization") String authorizationHeader, @Validated @RequestBody FeedUpdateRequest feedUpdateRequest){
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);
        return feedService.update(feedId, user, feedUpdateRequest);
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "피드 삭제")
    public String delete(@PathVariable Long feedId, @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        // 사용자 정보 가져오기
        User user = userService.getUserFromToken(token);
        return feedService.delete(feedId, user);
    }


}
