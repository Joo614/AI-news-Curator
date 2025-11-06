package com.aiproject.ai_news_curator.domain.interaction.entity;

import com.aiproject.ai_news_curator.domain.article.entity.Article;
import com.aiproject.ai_news_curator.domain.interaction.enums.InteractionType;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.enums.ProviderType;
import com.aiproject.ai_news_curator.global.audit.Auditable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
public class Interaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Enumerated(value = EnumType.STRING)
    private InteractionType interactionType;

    private Boolean isActive; // InteractionType의 행동 여부

    public void toggleLike() { // --> true, false 왔다갔다 변경 가능
        this.isActive = !this.isActive;
    }
}
