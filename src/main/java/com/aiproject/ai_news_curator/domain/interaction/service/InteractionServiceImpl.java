package com.aiproject.ai_news_curator.domain.interaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiproject.ai_news_curator.domain.article.repository.JpaArticleRepository;
import com.aiproject.ai_news_curator.domain.interaction.repository.JpaInteractionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {
    private final JpaInteractionRepository jpaInteractionRepository;
}
