package com.aiproject.ai_news_curator.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	private String email;

	private String password;
}