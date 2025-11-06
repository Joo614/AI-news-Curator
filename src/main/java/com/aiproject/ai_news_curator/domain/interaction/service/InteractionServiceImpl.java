package com.aiproject.ai_news_curator.domain.interaction.service;

import com.aiproject.ai_news_curator.domain.article.service.ArticleService;
import com.aiproject.ai_news_curator.domain.interaction.dto.InteractionReqDto;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.repository.JpaUserRepository;
import com.aiproject.ai_news_curator.domain.user.service.UserService;
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
    private final UserService userService;
    private final ArticleService articleService;

    @Override
    public void toggleLike(InteractionReqDto interactionReqDto, String email) {
        userService.verifyUser(email);
        // TODO: 기사 검증

        // qeuryDsl로 user, article, interactionType 조합해서 존재하는지 확인하고 가져옴 (find)
        // 존재하면 엔티티에 있는 toggleLike 버튼 딸깍해서 반대로 바꿔줌
        // 없으면 새로운 객체 생성
    }

    @Override
    public void activateRead(InteractionReqDto interactionReqDto, String email) {
        userService.verifyUser(email);
        // TODO: 기사 검증

        // qeuryDsl로 user, article, interactionType 조합해서 존재하는지 확인만 (exist)
        // 존재하면 (그럼 어차피 읽음 상태는 true니까) 바로 빠져나가기
        // 없으면 새로운 객체 생성
    }
}
