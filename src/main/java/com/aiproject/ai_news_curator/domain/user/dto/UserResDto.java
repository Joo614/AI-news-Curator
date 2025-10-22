package com.aiproject.ai_news_curator.domain.user.dto;

import com.aiproject.ai_news_curator.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
    private String email;

    private String nickname;


    @Builder
    public UserResDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
