package com.example.cherrypickserver.article.exception;

public class AlreadyAttendArticleException extends RuntimeException{
  public AlreadyAttendArticleException() {
    super("이미 좋아요 또는 스크랩을 누른 게시물입니다.");
  }
}
