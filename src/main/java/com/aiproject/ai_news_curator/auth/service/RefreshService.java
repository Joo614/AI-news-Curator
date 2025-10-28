package com.aiproject.ai_news_curator.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiproject.ai_news_curator.auth.entity.RefreshToken;
import com.aiproject.ai_news_curator.auth.repository.RefreshTokenRepository;
import com.aiproject.ai_news_curator.auth.token.AuthToken;
import com.aiproject.ai_news_curator.auth.token.AuthTokenProvider;
import com.aiproject.ai_news_curator.auth.utils.CookieUtils;
import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthTokenProvider authTokenProvider;

	@Transactional
	public void saveRefreshToken(String email, AuthToken authToken) {
		refreshTokenRepository.findById(email)
			.ifPresentOrElse(
				refreshToken -> {
					refreshToken.setToken(authToken.getToken());
					refreshToken.setExpiryDate(authToken.getValidTokenClaims().getExpiration());
				},
				() -> {
					RefreshToken refreshToken = RefreshToken.builder()
						.email(email)
						.token(authToken.getToken())
						.expiryDate(authToken.getValidTokenClaims().getExpiration())
						.build();
					refreshTokenRepository.save(refreshToken);
				}
			);
	}

	// refreshToken 으로 accessToken 재발급
	public void refresh(String refreshToken, HttpServletRequest request, HttpServletResponse response) {

		String accessToken = request.getHeader("Authorization"); // 만료된 access token 가져옴
		if (accessToken != null && accessToken.startsWith("Bearer ")) {
			accessToken = accessToken.substring(7); // "Bearer " 제거
		}

		AuthToken expiredAccessToken = authTokenProvider.convertAuthToken(accessToken);
		validateAccessTokenCheck(expiredAccessToken); // 만료 여부 확인

		// 쿠키에서 전달받은 Refresh Token 문자열을 AuthToken 객체로 변환
		AuthToken headerRefreshToken = authTokenProvider.convertAuthToken(refreshToken);

		// 만료된 Access Token 에서 이메일(Subject)과 권한(role) 추출
		String userEmail = expiredAccessToken.getExpiredTokenClaims().getSubject();
		List<String> roles = (List<String>)expiredAccessToken.getExpiredTokenClaims().get("role");

		// db에 저장된 refresh token 조회
		RefreshToken refreshTokenForReissue = refreshTokenRepository.findById(userEmail)
			.orElseThrow(() -> new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

		// db 토큰과 쿠키에서 가져온 토큰 일치여부/유효성 검사
		validateRefreshTokenCheck(refreshTokenForReissue, headerRefreshToken);


		// --- [보안 강화: Refresh Token Rotation (RTR)] ---
		AuthToken newAccessToken = authTokenProvider.createAccessToken(userEmail, roles); // 새 Access Token 생성
		AuthToken newRefreshToken = authTokenProvider.createRefreshToken(userEmail); // 새 Refresh Token 생성 (RTR)

		// DB에 새 Refresh Token으로 '교체' 저장 (RTR)
		saveRefreshToken(userEmail, newRefreshToken);

		// [10] 응답 헤더에 '새 Access Token' 추가
		response.addHeader("Authorization", "Bearer " + newAccessToken.getToken());

		int refreshTokenMaxAge = 604800; // 7일 (초 단위)
		CookieUtils.addCookie(response, "RefreshToken", newRefreshToken.getToken(), refreshTokenMaxAge);
	}

	public void validateAccessTokenCheck(AuthToken authToken) {
		if (!authToken.isTokenExpired())
			throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);

		if (authToken.getExpiredTokenClaims() == null)
			throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
	}

	public void validateRefreshTokenCheck(RefreshToken refreshToken, AuthToken headerRefreshToken) {
		if (!headerRefreshToken.isTokenValid())
			throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_INVALID);

		if (!refreshToken.getToken().equals(headerRefreshToken.getToken()))
			throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_MATCH);
	}
}
