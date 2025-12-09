package com.aiproject.ai_news_curator.domain.article.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.domain.article.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/articles")
@Validated
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // TODO : 그냥 분야별 최신 뉴스들 반환 / 키워드에 맞는 개인화된 뉴스들 목록 반환 (이것도 분야별로?)
}
