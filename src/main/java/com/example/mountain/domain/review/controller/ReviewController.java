package com.example.mountain.domain.review.controller;

import com.example.mountain.domain.image.service.ImageService;
import com.example.mountain.domain.review.dto.request.ReviewCreateRequest;
import com.example.mountain.domain.review.dto.request.ReviewUpdateRequest;
import com.example.mountain.domain.review.dto.response.ReviewDetailResponse;
import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.service.ReviewService;
import com.example.mountain.global.aws.S3Service;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = " 등반 리뷰(후기) API", description = "Review")
public class ReviewController {

    private final ReviewService reviewService;
    private final S3Service s3Service;
    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "리뷰 생성")
    public GlobalResponse create(@AuthenticationPrincipal CustomUserDetails user,
                                 @RequestPart ReviewCreateRequest reviewCreateRequest,
                                 @RequestPart(value = "imageUrl") List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            throw new CustomException(ErrorCode.NEED_FEED_IMAGE);
        }
        List<String> imgPaths = s3Service.upload(multipartFiles, "review");
        Long reviewId = reviewService.create(user.getUserId(), reviewCreateRequest, imgPaths);
        return GlobalResponse.success(reviewId);
    }

    @GetMapping
    @Operation(summary = "등반 리뷰 전체 조회")
    public GlobalResponse list(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "9") int pageSize,
                               @AuthenticationPrincipal CustomUserDetails user){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        Slice<ReviewListResponse> reviewListPage = reviewService.findList(pageable, user.getUserId());
        return GlobalResponse.success(reviewListPage);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "등반 리뷰 선택 조회")
    public GlobalResponse detail(@PathVariable Long reviewId, @AuthenticationPrincipal CustomUserDetails user){
        ReviewDetailResponse reviewDetailResponse = reviewService.findReview(reviewId, user.getUserId());
        return GlobalResponse.success(reviewDetailResponse);
    }

    @PutMapping(value ="/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "등반 리뷰 수정")
    public GlobalResponse update(@PathVariable Long reviewId, @AuthenticationPrincipal CustomUserDetails user,
                                 @Validated @RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                 @RequestPart(value = "imageUrl") List<MultipartFile> multipartFiles){
        List<String> imgPaths = null;
        if (multipartFiles!=null){
            imageService.deleteByReviewId(reviewId);
            imgPaths = s3Service.upload(multipartFiles, "feed");
        }

        reviewService.update(reviewId, user.getUserId(), reviewUpdateRequest,imgPaths);
        return GlobalResponse.success("등반 후기가 수정되었습니다.");
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "등반 리뷰 삭제")
    public GlobalResponse delete(@PathVariable Long reviewId, @AuthenticationPrincipal CustomUserDetails user){
        reviewService.delete(reviewId, user.getUserId());
        return GlobalResponse.success("성공적으로 삭제했습니다.");
    }

}
