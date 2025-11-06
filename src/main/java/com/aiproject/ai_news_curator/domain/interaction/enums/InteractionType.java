package com.aiproject.ai_news_curator.domain.interaction.enums;

import lombok.Getter;

@Getter
public enum InteractionType {
    LIKE("like"),
    READ("read");

    InteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    private String interactionType;
}
