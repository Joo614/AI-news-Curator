package com.aiproject.ai_news_curator.domain.userInterest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiproject.ai_news_curator.domain.userInterest.entity.UserInterest;

public interface JpaUserInterestRepository extends JpaRepository<UserInterest, Long>, UserInterestRepository {
}
