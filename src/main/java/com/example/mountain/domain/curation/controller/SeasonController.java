package com.example.mountain.domain.curation.controller;

import com.example.mountain.domain.curation.dto.SeasonResponse;
import com.example.mountain.domain.mountain.service.MountainService;
import com.example.mountain.global.dto.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curation")
@Tag(name = "계절별 메인 페이지", description = "curation")
public class SeasonController {

    private final MountainService mountainService;

    @GetMapping("/spring")
    @Operation(summary = "봄 조회")
    public GlobalResponse springList(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "3") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Slice<SeasonResponse> springList = mountainService.findSeasonList(pageable, "봄");

        return GlobalResponse.success(springList);
    }

    @GetMapping("/summer")
    @Operation(summary = "여름 조회")
    public GlobalResponse summerList(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "3") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Slice<SeasonResponse> summerList = mountainService.findSeasonList(pageable, "여름");

        return GlobalResponse.success(summerList);
    }

    @GetMapping("/autumn")
    @Operation(summary = "가을 조회")
    public GlobalResponse autumnList(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "3") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Slice<SeasonResponse> autumnList = mountainService.findSeasonList(pageable, "가을");

        return GlobalResponse.success(autumnList);
    }

    @GetMapping("/winter")
    @Operation(summary = "겨울 조회")
    public GlobalResponse winterList(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "3") int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Slice<SeasonResponse> winterList = mountainService.findSeasonList(pageable, "겨울");

        return GlobalResponse.success(winterList);
    }

}
