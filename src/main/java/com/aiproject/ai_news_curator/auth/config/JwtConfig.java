package com.aiproject.ai_news_curator.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aiproject.ai_news_curator.auth.service.CustomUserDetailService;
import com.aiproject.ai_news_curator.auth.token.AuthTokenProvider;

import lombok.Getter;

@Configuration
@Getter
public class JwtConfig {
	private final CustomUserDetailService customUserDetailService;
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long tokenValidTime;
	@Value("${jwt.refresh.expiration}")
	private Long refreshTokenValidTime;

	public JwtConfig(CustomUserDetailService customUserDetailService) {
		this.customUserDetailService = customUserDetailService;
	}

	@Bean
	public AuthTokenProvider authTokenProvider() {
		return new AuthTokenProvider(customUserDetailService, secret, tokenValidTime, refreshTokenValidTime);
	}
}
