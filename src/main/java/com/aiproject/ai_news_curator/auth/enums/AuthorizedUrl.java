package com.aiproject.ai_news_curator.auth.enums;

public enum AuthorizedUrl {
	LIKE("/like/**");

	private final String url;

	AuthorizedUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}
}
