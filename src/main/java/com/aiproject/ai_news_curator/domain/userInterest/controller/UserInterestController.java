package com.aiproject.ai_news_curator.domain.userInterest.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.domain.interaction.service.InteractionService;
import com.aiproject.ai_news_curator.domain.userInterest.service.UserInterestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/userInterests")
@Validated
@RequiredArgsConstructor
public class UserInterestController {
    private final UserInterestService userInterestService;
}
