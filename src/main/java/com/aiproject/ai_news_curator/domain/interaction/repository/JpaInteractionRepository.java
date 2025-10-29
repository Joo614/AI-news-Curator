package com.aiproject.ai_news_curator.domain.interaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiproject.ai_news_curator.domain.interaction.entity.Interaction;

public interface JpaInteractionRepository extends JpaRepository<Interaction, Long>, InteractionRepository {
}
