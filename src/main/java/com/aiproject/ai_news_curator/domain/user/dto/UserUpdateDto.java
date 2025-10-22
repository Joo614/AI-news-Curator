package com.aiproject.ai_news_curator.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class UserUpdateDto {
    @NotNull
    private String nickname;
}
