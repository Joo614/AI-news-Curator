package com.aiproject.ai_news_curator.domain.interaction.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.domain.interaction.service.InteractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interaction")
@Validated
@RequiredArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;
}
