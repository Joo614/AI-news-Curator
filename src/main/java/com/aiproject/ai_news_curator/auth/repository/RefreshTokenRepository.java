package com.aiproject.ai_news_curator.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiproject.ai_news_curator.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
