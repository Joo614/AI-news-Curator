package com.aiproject.ai_news_curator.domain.userInterest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiproject.ai_news_curator.domain.userInterest.repository.JpaUserInterestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserInterestServiceImpl implements UserInterestService {
    private final JpaUserInterestRepository jpaUserInterestRepository;
}
