package com.aiproject.ai_news_curator.auth.service;

import static com.aiproject.ai_news_curator.auth.utils.HeaderUtils.*;

import org.springframework.stereotype.Service;

import com.aiproject.ai_news_curator.auth.repository.RefreshTokenRepository;
import com.aiproject.ai_news_curator.auth.token.AuthToken;
import com.aiproject.ai_news_curator.auth.token.AuthTokenProvider;
import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthTokenProvider authTokenProvider;
	// TODO : 로그아웃 Redis 추가?

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		AuthToken accessToken = authTokenProvider.convertAuthToken(getAccessToken(request));

		if (!accessToken.isTokenValid())
			throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);

		String userEmail = accessToken.getValidTokenClaims().getSubject();

		refreshTokenRepository.deleteById(userEmail);
	}
}
