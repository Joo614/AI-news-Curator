package com.aiproject.ai_news_curator.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiproject.ai_news_curator.domain.article.entity.Article;

public interface JpaArticleRepository extends JpaRepository<Article, Long>, ArticleRepository {
}
