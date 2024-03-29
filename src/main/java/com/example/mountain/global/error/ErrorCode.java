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

    // Token
    INVALID_TOKEN(BAD_REQUEST, "Invalid Token"),

    // 유저
    NOT_FOUND_USER(BAD_REQUEST,  "해당 이름의 유저가 존재하지 않습니다."),
    PASSWORD_MISMATCH(BAD_REQUEST,  "비밀번호가 일치하지 않습니다"),

    //피드
    NOT_FOUND_FEED(BAD_REQUEST, "해당 피드가 존재하지 않습니다."),

    //산
    NOT_FOUND_MOUNTAIN(BAD_REQUEST, "해당 산이 존재하지 않습니다."),

    //모임
    NOT_FOUND_TEAM(BAD_REQUEST, "해당 모임이 존재하지 않습니다."),

    //성별
    NOT_FOUND_GENDER(BAD_REQUEST, "성별을 제대로 입력해주세요."),

    //연령
    NOT_FOUND_AGE(BAD_REQUEST, "연령을 다시 설정해주세요.");

    private final HttpStatus httpStatus;
    private final String detail;

}
