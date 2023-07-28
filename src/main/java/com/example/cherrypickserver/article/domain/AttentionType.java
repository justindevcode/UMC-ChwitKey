package com.example.cherrypickserver.article.domain;

import com.example.cherrypickserver.article.exception.AttentionTypeNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AttentionType {
  LIKE("like"),
  SCRAP("scrap");

  private final String name;

  private AttentionType(String name) {
    this.name = name;
  }

  public static AttentionType getAttentionTypeByName(String name){
    return Arrays.stream(AttentionType.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElseThrow(AttentionTypeNotFoundException::new);
  }
}

