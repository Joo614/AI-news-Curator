package com.aiproject.ai_news_curator.auth.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aiproject.ai_news_curator.auth.dto.LoginDto;
import com.aiproject.ai_news_curator.auth.service.RefreshService;
import com.aiproject.ai_news_curator.auth.token.AuthToken;
import com.aiproject.ai_news_curator.auth.token.AuthTokenProvider;
import com.aiproject.ai_news_curator.auth.utils.CookieUtils;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;
import com.aiproject.ai_news_curator.global.response.ErrorResponder;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthTokenProvider authTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final RefreshService refreshService;

	public JwtAuthenticationFilter(AuthTokenProvider authTokenProvider, AuthenticationManager authenticationManager,
		RefreshService refreshService) {
		this.authTokenProvider = authTokenProvider;
		this.authenticationManager = authenticationManager;
		this.refreshService = refreshService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		LoginDto loginDto = null;

		try {
			loginDto = gson.fromJson(request.getReader(), LoginDto.class);
		} catch (IOException e) {
			throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
		}

		if (loginDto == null) {
			try {
				ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			return null;
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			loginDto.getEmail(), loginDto.getPassword());

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		User user = (User)authResult.getPrincipal();
		AuthToken accessToken = authTokenProvider.createAccessToken(user.getEmail(),
			user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()));
		AuthToken refreshToken = authTokenProvider.createRefreshToken(user.getEmail());

		refreshService.saveRefreshToken(user.getEmail(), refreshToken);

		// access token 헤더에 추가
		response.addHeader("Authorization", "Bearer " + accessToken.getToken());

		// refresh token 쿠키에 추가
		int refreshTokenMaxAge = 604800; // 7일 (초 단위)
		CookieUtils.addCookie(response, "RefreshToken", refreshToken.getToken(), refreshTokenMaxAge);

		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}
}