package com.aiproject.ai_news_curator.domain.article.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // 중복 X
    private String name;

    @OneToMany(mappedBy = "keyword")
    private Set<ArticleKeyword> keywords = new HashSet<>();

    // 저장 시 띄어쓰기 빼고 / 다 소문자로 변경하고 / 중복 검사 후 저장
}
