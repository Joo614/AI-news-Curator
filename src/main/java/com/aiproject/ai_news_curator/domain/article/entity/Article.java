package com.aiproject.ai_news_curator.domain.article.entity;

import com.aiproject.ai_news_curator.domain.interaction.entity.Interaction;
import com.aiproject.ai_news_curator.global.audit.Auditable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
public class Article extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private String aiSummary;

    private Long likeCnt; // 좋아요 수

    private Long viewCnt; // 조회수

    @Column(nullable = false, unique = true)
    private String newsUrl;

    @Column(length = 100)
    private String publisher;

    private LocalDateTime publishedAt; // 기사 발행일 TODO 이거 있어야하나

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interaction> interactions = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleKeyword> keywords = new HashSet<>(); // set - 중복 방지

}
