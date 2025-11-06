package com.aiproject.ai_news_curator.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class PasswordUpdateDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
