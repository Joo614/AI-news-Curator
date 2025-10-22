package com.aiproject.ai_news_curator.domain.user.enums;

import lombok.Getter;

@Getter
public enum ProviderType {
    NATIVE("native"),
    GOOGLE("google"),
    KAKAO("kakao"),
    LINE("Line");

    ProviderType(String providerType) {
        this.providerType = providerType;
    }

    private String providerType;
}