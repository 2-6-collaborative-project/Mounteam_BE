package com.example.mountain.domain.review.controller;

import com.example.mountain.domain.image.service.ImageService;
import com.example.mountain.domain.review.dto.request.TeamReviewRequest;
import com.example.mountain.domain.review.dto.response.ReviewDetailResponse;
import com.example.mountain.domain.review.dto.response.ReviewListResponse;
import com.example.mountain.domain.review.service.ReviewService;
import com.example.mountain.domain.team.entity.Team;
import com.example.mountain.domain.team.service.TeamService;
import com.example.mountain.global.aws.S3Service;
import com.example.mountain.global.dto.GlobalResponse;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import com.example.mountain.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-reviews")
@Tag(name = " 모임(팀) 리뷰(후기) API", description = "Team Review")
public class TeamReviewController {

    private final ReviewService reviewService;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final TeamService teamService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "모임(팀) 리뷰 생성")
    public GlobalResponse createTeamReview(@AuthenticationPrincipal CustomUserDetails user,
                                           @RequestPart TeamReviewRequest teamReviewRequest,
                                           @RequestPart(value = "imageUrl") List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            throw new CustomException(ErrorCode.NEED_FEED_IMAGE);
        }
        Team team = teamService.findById(teamReviewRequest.getTeamId());
        List<String> imgPaths = s3Service.upload(multipartFiles, "team-review");
        Long reviewId = reviewService.createTeamReview(user.getUserId(), team, teamReviewRequest, imgPaths);
        return GlobalResponse.success(reviewId);
    }

    @GetMapping
    @Operation(summary = "모임(팀) 리뷰 전체 조회")
    public GlobalResponse list(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "9") int pageSize,
                               @AuthenticationPrincipal CustomUserDetails user){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        Slice<ReviewListResponse> reviewListPage = reviewService.findTeamList(pageable, user.getUserId());
        return GlobalResponse.success(reviewListPage);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "모임(팀) 리뷰 선택 조회")
    public GlobalResponse detail(@PathVariable Long reviewId, @AuthenticationPrincipal CustomUserDetails user){
        ReviewDetailResponse reviewDetailResponse = reviewService.findTeamReview(reviewId, user.getUserId());
        return GlobalResponse.success(reviewDetailResponse);
    }


}
