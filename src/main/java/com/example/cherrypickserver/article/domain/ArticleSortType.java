package com.example.cherrypickserver.article.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleSortType {
  ASC("ASC"),
  DESC("DESC"),
  LIKE("LIKE");

  private final String description;
}
