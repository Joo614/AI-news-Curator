package com.aiproject.ai_news_curator.auth.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.aiproject.ai_news_curator.global.response.ErrorResponse;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.info("LOGIN FAILED : " + exception.getMessage());
		sendErrorResponse(response);
	}

	private void sendErrorResponse(HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED, "LOGIN FAILED");
		response.setStatus(errorResponse.getStatus());
		response.getWriter().write(gson.toJson(errorResponse));
	}
}
