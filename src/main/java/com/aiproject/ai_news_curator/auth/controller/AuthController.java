package com.aiproject.ai_news_curator.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.auth.service.AuthService;
import com.aiproject.ai_news_curator.auth.service.RefreshService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	private final RefreshService refreshService;

	@PostMapping("/refresh")
	public ResponseEntity<String> refresh(
		@CookieValue(value = "RefreshToken") String refreshToken,
		HttpServletRequest request,
		HttpServletResponse response) {

		System.out.println("리프레시 토큰 ----------------------------- : " + refreshToken);

		refreshService.refresh(refreshToken, request, response);
		return ResponseEntity.ok("성공적으로 재발급되었습니다.");
	}

	// @PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		authService.logout(request, response);
		// TODO 로그아웃 --> 리프레시 토큰 삭제
		return ResponseEntity.ok().build();
	}

}
