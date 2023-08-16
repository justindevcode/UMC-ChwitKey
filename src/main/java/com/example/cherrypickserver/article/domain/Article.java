package com.example.cherrypickserver.article.domain;


import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

  private String title;

  @Column(columnDefinition="TEXT")
  private String contents;

  private String publisher;

  private String reporter;

  private LocalDateTime uploadedAt;

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  private List<ArticlePhoto> articlePhoto = new ArrayList<>();
  @Enumerated(EnumType.STRING)

  @Column(name = "industry")
  private Industry industry;

  @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
  private int likeCount = 0;

  @Builder
  public Article(String contents, String title, String publisher, String reporter, LocalDateTime uploadedAt, int likeCount, Industry industry) {
    this.contents = contents;
    this.title = title;
    this.publisher = publisher;
    this.reporter = reporter;
    this.uploadedAt = uploadedAt;
    this.likeCount = likeCount;
    this.industry = industry;
  }

  public void likeArticle() {
    this.likeCount += 1;
  }

  public void unLikeArticle() {
    this.likeCount -= 1;
  }
}

