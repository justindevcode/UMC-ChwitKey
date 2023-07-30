package com.example.cherrypickserver.article.exception;

public class IndustryNotFoundException extends RuntimeException {
    public IndustryNotFoundException() {
      super("Industry를 찾을 수 없습니다.");
    }
}
