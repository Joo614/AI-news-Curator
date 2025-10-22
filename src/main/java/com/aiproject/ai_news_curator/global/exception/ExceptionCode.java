package com.aiproject.ai_news_curator.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

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
