package com.aiproject.ai_news_curator.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class NicknameUpdateDto {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
