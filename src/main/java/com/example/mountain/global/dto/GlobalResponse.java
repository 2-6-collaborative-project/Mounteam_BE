package com.example.mountain.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class GlobalResponse<T> {

    public static final GlobalResponse<String> DEFAULT_OK = new GlobalResponse<>(GlobalResponseCode.OK);

    private String msg;
    private int statusCode;
    private T data;

    public GlobalResponse(GlobalResponseCode globalResponseCode) {
        this.statusCode = globalResponseCode.getCode();
        this.msg = globalResponseCode.getMessage();
    }
    public GlobalResponse(GlobalResponseCode globalResponseCode, T data) {
        this.msg = globalResponseCode.getMessage();
        this.statusCode = globalResponseCode.getCode();
        this.data = data;
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(GlobalResponseCode.OK, data);
    }

    public static <T> GlobalResponse<T> success() {
        return new GlobalResponse<>(GlobalResponseCode.OK);
    }

}
