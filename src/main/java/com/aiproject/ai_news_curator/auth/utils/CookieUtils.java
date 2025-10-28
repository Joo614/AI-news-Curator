package com.aiproject.ai_news_curator.auth.utils;

import java.util.Base64;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtils {
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return Optional.of(cookie);
				}
			}
		}
		return Optional.empty();
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.sameSite("Lax") // TODO: 배포 시 HTTPS 적용 후 None로 변경
			.secure(false) // TODO: 배포 시 HTTPS 적용 후 true로 변경
			.path("/")
			.maxAge(maxAge)
			.httpOnly(true)
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public static String serialize(Object obj) {
		return Base64.getUrlEncoder()
			.encodeToString(SerializationUtils.serialize(obj));
	}

	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(
			SerializationUtils.deserialize(
				Base64.getUrlDecoder().decode(cookie.getValue())
			)
		);
	}

}