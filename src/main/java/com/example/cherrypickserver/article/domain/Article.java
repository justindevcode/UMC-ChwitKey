package com.example.cherrypickserver.article.domain;


import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id")
  private Long id;

  @Column(columnDefinition="TEXT")
  private String contents;

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  private List<ArticlePhoto> articlePhoto = new ArrayList<>();

  private String articleName;
  private String publisher;
  private String reporter;
  private Date registeredAt;

  @Builder
  public Article(String contents, String articleName, String publisher, String reporter, Date registeredAt) {
    this.contents = contents;
    this.articleName = articleName;
    this.publisher = publisher;
    this.reporter = reporter;
    this.registeredAt = registeredAt;
  }
}

