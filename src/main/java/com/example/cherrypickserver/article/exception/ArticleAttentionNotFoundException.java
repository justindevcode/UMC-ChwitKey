package com.example.cherrypickserver.article.exception;

public class ArticleAttentionNotFoundException extends RuntimeException{
  public ArticleAttentionNotFoundException() {
    super("좋아요 또는 스크랩을 누른 게시물이 아닙니다.");
  }
}
