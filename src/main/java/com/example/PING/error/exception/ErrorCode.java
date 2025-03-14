package com.example.PING.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Test"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 엔티티를 찾을 수 없습니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 인증 정보입니다"),


    //Auth
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "시큐리티 인증 정보를 찾을 수 없습니다."),
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 에러"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 Token입니다"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 길이 및 형식이 다른 Token입니다"),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "서명이 잘못된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "토큰이 없습니다"),
    TOKEN_SUBJECT_FORMAT_ERROR(HttpStatus.UNAUTHORIZED, "Subject 값에 Long 타입이 아닌 다른 타입이 들어있습니다."),
    AT_EXPIRED_AND_RT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AT는 만료되었고 RT는 비어있습니다."),
    RT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "RT가 비어있습니다"),

    //OAuth
    KAKAO_TOKEN_CLIENT_FAILED(HttpStatus.UNAUTHORIZED, "카카오 AT 인증이 실패했습니다."),
    AUTH_GET_USER_INFO_FAILED(HttpStatus.UNAUTHORIZED, "SocialAccessToken을 통해 사용자 정보를 가져오는 데에 실패했습니다."),
    INVALID_PROVIDER_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 provider를 입력하셨습니다."),
    PASSWORD_NOT_MATCHES(HttpStatus.BAD_REQUEST, "비밀번호를 잘못 입력하셨습니다."),

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    ONBOARD_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 온보딩 상태를 찾을 수 없습니다. (잘못된 온보딩 상태를 입력하셨습니다)")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
