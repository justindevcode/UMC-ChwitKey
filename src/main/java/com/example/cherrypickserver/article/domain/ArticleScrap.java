package com.example.cherrypickserver.article.domain;


import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.global.entity.BaseEntity;
import com.example.cherrypickserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ArticleScrap extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "articleScrap_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="article_id")
  private Article article;


}
