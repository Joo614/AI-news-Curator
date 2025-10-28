package com.aiproject.ai_news_curator.auth.token;

import java.security.Key;
import java.util.Date;
import java.util.List;

import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthToken {
	@Getter
	private final String token;
	private final Key key;
	private static final String AUTHORITIES_KEY = "role";
	private Claims claims;
	private boolean isExpired;
	private boolean isParsed = false;

	public AuthToken(String token, Key key) {
		this.key = key;
		this.token = token;
	}

	AuthToken(String id, Date expiry, Key key) {
		this.key = key;
		this.token = createAccessToken(id, expiry);
	}

	AuthToken(String id, String role, Date expiry, Key key) {
		this.key = key;
		this.token = createAccessToken(id, role, expiry);
	}

	AuthToken(String id, List<String> roles, Date expiry, Key key) {
		this.key = key;
		this.token = createAccessToken(id, roles, expiry);
	}

	private String createAccessToken(String id, Date expiry) {
		return Jwts.builder()
			.setSubject(id)
			.signWith(key,
				SignatureAlgorithm.HS256)
			.setExpiration(expiry)
			.compact();
	}

	private String createAccessToken(String id, String role, Date expiry) {
		return Jwts.builder()
			.setSubject(id)
			.claim(AUTHORITIES_KEY, role)
			.signWith(key, SignatureAlgorithm.HS256)
			.setExpiration(expiry)
			.compact();
	}

	private String createAccessToken(String id, List<String> roles, Date expiry) {
		return Jwts.builder()
			.setSubject(id)
			.claim(AUTHORITIES_KEY, roles)
			.signWith(key, SignatureAlgorithm.HS256)
			.setExpiration(expiry)
			.compact();
	}

	// 유효한지 확인용
	public boolean isTokenValid() {
		parseToken(); // 파싱 (필요시)
		return this.claims != null && !this.isExpired;
	}

	// 만료되었는지 확인용
	public boolean isTokenExpired() {
		parseToken(); // 파싱 (필요시)
		return this.claims != null && this.isExpired;
	}

	// 유효한 토큰 데이터 추출용
	public Claims getValidTokenClaims() {
		parseToken(); // 파싱 (필요시)

		if (this.claims != null && !this.isExpired) {
			return this.claims;
		}
		return null; // 또는 예외 발생
	}

	// 만료된 토큰 데이터 추출용
	public Claims getExpiredTokenClaims() {
		parseToken(); // 파싱 (필요시)

		if (this.claims != null && this.isExpired) {
			return this.claims;
		}
		return null;
	}

	private void parseToken() {
		if (isParsed) {
			return; // 이미 파싱됐다면 즉시 반환
		}

		try { // 파싱 시도 (토큰 읽기)
			this.claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
			this.isExpired = false; // 성공 -> 만료 안됨
		} catch (ExpiredJwtException e) { // 만료된 토큰일 경우
			log.info("Expired JWT token.");
			this.claims = e.getClaims(); // 만료된 토큰도 클레임은 저장
			this.isExpired = true; // 만료된 true
		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | io.jsonwebtoken.security.SignatureException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
			this.claims = null; // 유효하지 않은 토큰
			this.isExpired = false; // 의미 없음
			if (e instanceof io.jsonwebtoken.security.SignatureException) {
				throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
			}
		}
		this.isParsed = true; // 어쨌든 파싱이 끝났으니 true로 (만료되었을 때도 이 사실 자체를 파싱해서 저장)
	}

	@Override
	public String toString() {
		return token;
	}
}
