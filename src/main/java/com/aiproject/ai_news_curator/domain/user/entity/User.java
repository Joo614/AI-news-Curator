package com.aiproject.ai_news_curator.domain.user.entity;

import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String nickname;

    // role - 어드민 / 일반 유저
    // 프로필 이미지 할까 말까
    // userStatus - 휴먼,탙퇴 이런거
    // oauth2 관련

    // 로그인 providerType 할까 말까 ex. native / google / kakao

    public void update(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getNickname() != null)
            this.nickname = userUpdateDto.getNickname();
    }
}
