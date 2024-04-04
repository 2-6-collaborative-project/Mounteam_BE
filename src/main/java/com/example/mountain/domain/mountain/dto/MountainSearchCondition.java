package com.example.mountain.domain.mountain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MountainSearchCondition {
    private Long cursor;
    private Integer high;
    private String areaInterest;


    public static MountainSearchCondition of(Long cursor, Integer high, String areaInterest){
        return MountainSearchCondition.builder()
                .cursor(cursor)
                .high(high)
                .areaInterest(areaInterest)
                .build();
    }
}
