package com.aiproject.ai_news_curator.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiproject.ai_news_curator.domain.user.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    Optional<User> findByEmail(String email);
}
