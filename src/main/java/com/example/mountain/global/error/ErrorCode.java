package com.example.mountain.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(BAD_REQUEST, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(BAD_REQUEST,  " Invalid Input Value"),
    ENTITY_NOT_FOUND(BAD_REQUEST,  " Entity Not Found"),
    INVALID_TYPE_VALUE(BAD_REQUEST, " Invalid Type Value"),
    ERROR_PARSING_JSON_RESPONSE(BAD_REQUEST,"Error Parsing JSON Response"),
    MISSING_INPUT_VALUE(BAD_REQUEST, "Missing Input Value"),

    // Token
    INVALID_TOKEN(BAD_REQUEST, "Invalid Token"),
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "ACCESS_TOKEN_EXPIRED"),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED"),

    // 유저
    NOT_FOUND_USER(BAD_REQUEST,  "해당 이름의 유저가 존재하지 않습니다."),
    PASSWORD_MISMATCH(BAD_REQUEST,  "비밀번호가 일치하지 않습니다"),

    //피드
    NOT_FOUND_FEED(BAD_REQUEST, "해당 피드가 존재하지 않습니다."),
    NOT_MATCH_FEED_USER_UPDATE(BAD_REQUEST, "자신이 작성한 피드만 수정가능합니다."),
    NOT_MATCH_FEED_USER_DELETE(BAD_REQUEST, "자신이 작성한 피드만 삭제가능합니다."),
    NEED_FEED_IMAGE(BAD_REQUEST,"피드에 이미지가 최소 한개이상 필요합니다(최대5장)"),
    WRONG_INPUT_IMAGE(BAD_REQUEST, "이미지 값이 잘못되었습니다."),

    //산
    NOT_FOUND_MOUNTAIN(BAD_REQUEST, "해당 산이 존재하지 않습니다."),

    //모임
    NOT_FOUND_TEAM(BAD_REQUEST, "해당 모임이 존재하지 않습니다."),
    NOT_MATCH_TEAM_USER_UPDATE(BAD_REQUEST,"자신이 작성한 모임만 수정가능합니다."),
    NOT_MATCH_TEAM_USER_DELETE(BAD_REQUEST,"자신이 작성한 모임만 삭제가능합니다."),
    NEED_TEAM_IMAGE(BAD_REQUEST, "모임 대표 이미지가 필요합니다." ),
    //성별
    NOT_FOUND_GENDER(BAD_REQUEST, "성별을 제대로 입력해주세요."),

    //연령
    NOT_FOUND_AGE(BAD_REQUEST, "연령을 다시 설정해주세요."),

    //리뷰
    NOT_FOUND_REIVEW(BAD_REQUEST, "해당 리뷰가 존재하지 않습니다."),
    NOT_MATCH_REVIEW_USER_UPDATE(BAD_REQUEST,"자신이 작성한 리뷰만 수정가능합니다." ),
    NOT_MATCH_REVIEW_USER_DELETE(BAD_REQUEST,"자신이 작성한 리뷰만 삭제가능합니다." ),

    //모임참여
    NOT_VALID_GENDER(BAD_REQUEST, "모임과 본인 성별이 맞지 않습니다." ),
    NOT_VALID_AGE_RANGE(BAD_REQUEST, "모임과 본인 나이대가 맞지 않습니다." );


    private final HttpStatus httpStatus;
    private final String message;

}
