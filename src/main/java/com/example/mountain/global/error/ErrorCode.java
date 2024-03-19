package com.example.mountain.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(BAD_REQUEST, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(BAD_REQUEST,  " Invalid Input Value"),
    ENTITY_NOT_FOUND(BAD_REQUEST,  " Entity Not Found"),
    INVALID_TYPE_VALUE(BAD_REQUEST, " Invalid Type Value"),
    ERROR_PARSING_JSON_RESPONSE(BAD_REQUEST,"Error Parsing JSON Response"),

    // 유저
    NOTFOUND_USER(BAD_REQUEST,  "해당 이름의 유저가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String detail;

}
