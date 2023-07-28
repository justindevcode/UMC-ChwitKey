package com.example.cherrypickserver.article.exception;

public class AttentionTypeNotFoundException extends RuntimeException {
  public AttentionTypeNotFoundException() {
    super("해당하는 Attention Type을 찾을 수 없습니다.");
  }
}
