package com.aiproject.ai_news_curator.domain.user.entity;

import com.aiproject.ai_news_curator.auth.entity.Authorities;
import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import com.aiproject.ai_news_curator.domain.user.enums.ProviderType;
import com.aiproject.ai_news_curator.domain.user.enums.UserRole;
import com.aiproject.ai_news_curator.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true, length = 20)
    private ProviderType providerType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true, length = 20)
    private UserStatus userStatus = UserStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authorities> roles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UserRole role;

    // 프로필 이미지 할까 말까
    // oauth2 관련

    // 로그인 providerType 할까 말까 ex. native / google / kakao

//    public String getRoleKey() {
//        return this.role.getKey();
//    }

    public void update(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getNickname() != null)
            this.nickname = userUpdateDto.getNickname();
    }
}
