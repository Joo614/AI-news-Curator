package com.aiproject.ai_news_curator.domain.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiproject.ai_news_curator.domain.article.repository.JpaArticleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final JpaArticleRepository jpaArticleRepository;
}
