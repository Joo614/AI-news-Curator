package com.aiproject.ai_news_curator.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    MEMBER_ACTIVE("활동중"),
//    MEMBER_SLEEP("휴먼 상태"),
    MEMBER_QUIT("탈퇴 상태");

    @Getter
    private String status;

    UserStatus(String status) {
        this.status = status;
    }
}