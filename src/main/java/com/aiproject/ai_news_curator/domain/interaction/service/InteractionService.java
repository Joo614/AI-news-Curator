package com.aiproject.ai_news_curator.domain.interaction.service;


import com.aiproject.ai_news_curator.domain.interaction.dto.InteractionReqDto;

public interface InteractionService {
    void toggleLike(InteractionReqDto interactionReqDto, String email);

    void activateRead(InteractionReqDto interactionReqDto, String email);
}
