package com.example.cherrypickserver.article.domain;

import com.example.cherrypickserver.article.exception.AttentionTypeNotFoundException;
import com.example.cherrypickserver.article.exception.SortTypeNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortType {
  ASC("asc"),
  DESC("desc"),
  LIKE("like");

  private final String name;

  private SortType(String name) {
    this.name = name;
  }

  public static SortType getSortTypeByName(String name){
    return Arrays.stream(SortType.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElseThrow(SortTypeNotFoundException::new);
  }
}

