package com.aiproject.ai_news_curator.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // JWT Token
    TOKEN_INVALID(401, "TOKEN_INVALID"),
    TOKEN_NOT_EXPIRED(400, "TOKEN_NOT_EXPIRED"),
    REFRESH_TOKEN_NOT_FOUND(400, "REFRESH_TOKEN_NOT_FOUND"), REFRESH_TOKEN_INVALID(400, "REFRESH_TOKEN_INVALID"),
    REFRESH_TOKEN_NOT_MATCH(400, "REFRESH_TOKEN_NOT_MATCH"),

    INVALID_ELEMENT(400, "INVALID_ELEMENT"),

    // USER
    USER_NONE(404, "USER_NONE"),
    USER_DUPLICATED(409, "USER_DUPLICATED");

    @Getter
    private final int code;

    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
