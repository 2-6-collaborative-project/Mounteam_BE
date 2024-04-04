package com.example.mountain.global.dto;

import com.example.mountain.global.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResponse<T> {

    private String msg;
    private int statusCode;
    private T data;

    public GlobalResponse(GlobalResponseCode globalResponseCode) {
        this.msg = globalResponseCode.getMessage();
        this.statusCode = globalResponseCode.getCode();
    }
    public GlobalResponse(GlobalResponseCode globalResponseCode, T data) {
        this.msg = globalResponseCode.getMessage();
        this.statusCode = globalResponseCode.getCode();
        this.data = data;
    }

    public GlobalResponse(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public GlobalResponse(String msg, int statusCode, T data) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(GlobalResponseCode.OK, data);
    }

    public static <T> GlobalResponse<T> success() {
        return new GlobalResponse<>(GlobalResponseCode.OK);
    }
    public static <T> GlobalResponse<T> error(GlobalResponseCode globalResponseCode, T data) {
        return new GlobalResponse<>(globalResponseCode, data);
    }

    public static <T> GlobalResponse<T> error(GlobalResponseCode globalResponseCode) {
        return new GlobalResponse<>(globalResponseCode);
    }
    public static <T> GlobalResponse<T> error(String msg, ErrorCode errorCode) {
        return new GlobalResponse<>(msg, errorCode.getHttpStatus().value());
    }
}
