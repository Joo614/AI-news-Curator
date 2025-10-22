package com.aiproject.ai_news_curator.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER", "일반유저"),
    ADMIN("ROLE_ADMIN", "어드민유저");

    private final String key;
    private final String value;

}
