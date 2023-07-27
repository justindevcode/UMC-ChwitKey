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
  private String content;

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  private List<ArticlePhoto> articlePhoto = new ArrayList<>();

  @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
  private Integer likeCount = 0;

  private String title;
  private String publisher;
  private String reporter;
  private Date registeredAt;

  @Builder
  public Article(String content, String title, String publisher, String reporter, Date registeredAt) {
    this.content = content;
    this.title = title;
    this.publisher = publisher;
    this.reporter = reporter;
    this.registeredAt = registeredAt;
  }

  public void likeArticle() {
    this.likeCount += 1;
  }

  public void unLikeArticle() {
    this.likeCount -= 1;
  }
}

