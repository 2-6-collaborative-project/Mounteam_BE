package com.example.mountain.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalResponseCode {
    OK(200,"요청이 성공하였습니다.");

    private final int code;
    private final String message;
}
