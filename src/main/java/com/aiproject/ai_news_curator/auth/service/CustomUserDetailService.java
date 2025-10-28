package com.aiproject.ai_news_curator.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aiproject.ai_news_curator.auth.entity.UserPrincipal;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.repository.JpaUserRepository;
import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;

@Service
public class CustomUserDetailService implements UserDetailsService {
	private final JpaUserRepository userRepository;

	public CustomUserDetailService(JpaUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new CustomLogicException(ExceptionCode.USER_NONE));
		return UserPrincipal.create(user);
	}
}
