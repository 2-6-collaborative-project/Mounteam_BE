package com.example.mountain.domain.feed.controller;

import com.example.mountain.domain.feed.dto.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.FeedListResponse;
import com.example.mountain.domain.feed.dto.FeedUpdateRequest;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
@Tag(name = "FeedController", description = "피드")
public class FeedController {

    private final FeedService feedService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<GlobalResponse<String>> create(@RequestParam("userId") Long userId, @RequestBody FeedCreateRequest feedCreateRequest) {
        User user = userService.getUserById(userId);
        feedService.create(user, feedCreateRequest);
        return ResponseEntity.ok(GlobalResponse.success());
    }

    @GetMapping
    public ResponseEntity<FeedListResponse> list(User user){
        FeedListResponse feedList = feedService.findList();
        return ResponseEntity.ok(feedList);
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedDetailResponse> detail(@PathVariable Long feedId, User user){
        return ResponseEntity.ok(feedService.findFeed(feedId, user));
    }

    @PutMapping("/{feedId}")
    public String update(@PathVariable Long feedId, User user, @Validated @RequestBody FeedUpdateRequest feedUpdateRequest){
        return feedService.update(feedId, user, feedUpdateRequest);
    }

    @DeleteMapping("/{feedId}")
    public String delete(@PathVariable Long feedId, User user){
        return feedService.delete(feedId, user);
    }


}
