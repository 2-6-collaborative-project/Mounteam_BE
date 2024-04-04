package com.example.mountain.domain.mountain.controller;

import com.example.mountain.domain.mountain.service.MountainService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/explores")
@Tag(name = "탐험 API")
public class MountainController {

    private final MountainService mountainService;

    @GetMapping("/search")
    @Operation(summary = "탐험 산 데이터 검색") // 리팩토링 예정 일단 100개의 산 이름을 보낸다.
    public GlobalResponse<?> autoComplete() {
        return GlobalResponse.success(mountainService.autoComplete());
    }

    @GetMapping
    @Operation(summary = "탐험 산 리스트")
    public GlobalResponse<?> getMountainList(@RequestParam(required = false) String areaInterest,
                                             @RequestParam(required = false) Integer high,
                                             @RequestParam(required = false) String orderBy,
                                             @RequestParam(required = false) Long cursor,
                                             Pageable pageable) {
        return GlobalResponse.success(mountainService.getMountainList(areaInterest, high, orderBy, cursor, pageable));
    }

    @GetMapping("/{mountainId}")
    @Operation(summary = "탐험 산 상세보기")
    public GlobalResponse<?> getMountainDetail(@PathVariable Long mountainId) {
        return GlobalResponse.success(mountainService.getMountainDetail(mountainId));
    }
}
