package com.example.cherrypickserver.article.domain;


import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "like_id")
  private Long id;

  @Column(columnDefinition="TEXT")
  private String contents;

  private String articleName;
  private String publisher;
  private String articleImgKey;

}

