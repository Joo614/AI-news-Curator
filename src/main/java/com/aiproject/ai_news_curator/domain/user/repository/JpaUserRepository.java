package com.aiproject.ai_news_curator.domain.user.repository;

import com.aiproject.ai_news_curator.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
