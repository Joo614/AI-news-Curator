package com.aiproject.ai_news_curator.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aiproject.ai_news_curator.auth.enums.AuthorizedUrl;
import com.aiproject.ai_news_curator.auth.filter.JwtAuthenticationFilter;
import com.aiproject.ai_news_curator.auth.filter.JwtVerificationFilter;
import com.aiproject.ai_news_curator.auth.handler.UserAuthenticationFailureHandler;
import com.aiproject.ai_news_curator.auth.handler.UserAuthenticationSuccessHandler;
import com.aiproject.ai_news_curator.auth.service.RefreshService;
import com.aiproject.ai_news_curator.auth.token.AuthTokenProvider;
import com.aiproject.ai_news_curator.auth.handler.JwtAccessDeniedHandler;
import com.aiproject.ai_news_curator.auth.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthTokenProvider authTokenProvider;
	private final RefreshService refreshService;
	private final JwtConfig jwtConfig;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private static final String[] AUTHORIZED_URLS = Arrays.stream(AuthorizedUrl.values())
		.map(AuthorizedUrl::getUrl)
		.toList().toArray(new String[0]);


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
		AuthenticationManager authenticationManager) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authTokenProvider,
			authenticationManager, refreshService);
		jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
		jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
		jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());
		JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(authTokenProvider);

		http.csrf(csrf -> csrf.disable())
			.cors(Customizer.withDefaults())
			.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(form -> form.disable())
			.httpBasic(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtVerificationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilter(jwtAuthenticationFilter)
			.exceptionHandling(
				exceptionHandling -> exceptionHandling
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler))
			.authorizeHttpRequests(
				authorize -> authorize
					.requestMatchers(
						AUTHORIZED_URLS
					).authenticated()
					.anyRequest().permitAll()
			)//여기부터 추가
			.logout(logout -> logout
				.logoutSuccessUrl("/")// 로그아웃 성공시 해당 주소로 이동
			);
		// .oauth2Login(oauth2Login -> oauth2Login// OAuth2 로그인 기능에 대한 여러 설정의 진입점
		// 		.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint  // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
		// 			.userService(customOauth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 userService 인터페이스의 구현체 등록
		// 		) // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능.
		// 		.successHandler(oAuth2AuthenticationSuccessHandler())
		// 		.failureHandler(oAuth2AuthenticationFailureHandler())
		// 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능.
		//);
		//					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		// 					.accessDeniedHandler(jwtAccessDeniedHandler)) //요기다 추가!
		return http.build();
	}

}
