package com.example.mountain.domain.mountain.controller;

import com.example.mountain.domain.mountain.service.MountainService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
                                             @RequestParam(required = false) String high,
                                             @RequestParam(required = false) String orderBy,
                                             @RequestParam(required = false) Long cursor,
                                             Pageable pageable) {
        return GlobalResponse.success(mountainService.getMountainList(areaInterest, high, orderBy, cursor, pageable));
    }
}
