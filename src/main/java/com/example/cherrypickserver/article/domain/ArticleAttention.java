package com.example.cherrypickserver.article.domain;


import com.example.cherrypickserver.global.entity.BaseEntity;
import com.example.cherrypickserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticleAttention extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "articleAttention_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="article_id")
  private Article article;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AttentionType attentionType;

  @Builder
  public ArticleAttention(Member member, Article article, AttentionType attentionType) {
    this.member = member;
    this.article = article;
    this.attentionType = attentionType;
  }
}
