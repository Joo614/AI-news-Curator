package com.aiproject.ai_news_curator.domain.article.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.domain.article.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/article")
@Validated
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
}
