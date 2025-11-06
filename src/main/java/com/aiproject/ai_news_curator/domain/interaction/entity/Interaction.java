package com.aiproject.ai_news_curator.domain.interaction.entity;

import com.aiproject.ai_news_curator.domain.interaction.enums.InteractionType;
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


    @Enumerated(value = EnumType.STRING)
    private InteractionType interactionType;
}
