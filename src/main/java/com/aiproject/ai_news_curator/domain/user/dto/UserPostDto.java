package com.aiproject.ai_news_curator.domain.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class UserPostDto {
    @NotNull
    @Email
    @Size(min = 4, message = "이메일은 최소 4자 이상이어야 합니다.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하입니다.")
    private String password;

    @NotNull
    private String nickname;
}
