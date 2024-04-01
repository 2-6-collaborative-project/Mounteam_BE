package com.example.mountain.domain.feed.controller;

import com.example.mountain.domain.feed.dto.request.FeedCreateRequest;
import com.example.mountain.domain.feed.dto.response.FeedDetailResponse;
import com.example.mountain.domain.feed.dto.response.FeedListResponse;
import com.example.mountain.domain.feed.dto.request.FeedUpdateRequest;
import com.example.mountain.domain.feed.service.FeedService;
import com.example.mountain.domain.image.service.ImageService;
import com.example.mountain.global.aws.S3Service;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "피드 API", description = "피드")
public class FeedController {

    private final FeedService feedService;
    private final S3Service s3Service;
    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "피드 작성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                 @RequestPart FeedCreateRequest feedCreateRequest,
                                 @RequestPart(value = "imageUrl") List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            throw new CustomException(ErrorCode.NEED_FEED_IMAGE);
        }
        List<String> imgPaths = s3Service.upload(multipartFiles, "feed");
        Long feedId = feedService.create(user.getUserId(), feedCreateRequest, imgPaths);
        return GlobalResponse.success(feedId);
    }

    @GetMapping
    @Operation(summary = "피드 전체 조회")
    public GlobalResponse list(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "9") int pageSize,
                               @AuthenticationPrincipal CustomUserDetails user){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        Page<FeedListResponse> feedListPage = feedService.findList(pageable, user.getUserId());
        return GlobalResponse.success(feedListPage);
    }

    @GetMapping("/{feedId}")
    @Operation(summary = "피드 선택 조회")
    public GlobalResponse detail(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user){
        FeedDetailResponse feedDetailResponse = feedService.findFeed(feedId, user.getUserId());

        return GlobalResponse.success(feedDetailResponse);
    }

    @PutMapping(value = "/{feedId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "피드 수정", description = "내용,이미지 수정가능")
    public GlobalResponse update(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user,
                                 @RequestPart FeedUpdateRequest feedUpdateRequest,
                                 @RequestPart(value = "imageUrl") List<MultipartFile> multipartFiles){
        List<String> imgPaths = null;
        if (multipartFiles!=null){
            imageService.deleteByFeedId(feedId);
            imgPaths = s3Service.upload(multipartFiles, "feed");
        }

        feedService.update(feedId, user.getUserId(), feedUpdateRequest, imgPaths);

        return GlobalResponse.success("피드가 수정되었습니다");
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "피드 삭제")
    public GlobalResponse delete(@PathVariable Long feedId, @AuthenticationPrincipal CustomUserDetails user){
        feedService.delete(feedId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }
}
