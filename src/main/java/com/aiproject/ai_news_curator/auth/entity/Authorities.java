package com.aiproject.ai_news_curator.auth.entity;

import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.enums.UserRole;
import com.aiproject.ai_news_curator.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Authorities extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_email")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public Authorities(User user, String role) {
        this.user = user;
        this.role = UserRole.valueOf(role);
    }
}
