package com.aiproject.ai_news_curator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AiNewsCuratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiNewsCuratorApplication.class, args);
	}

}
