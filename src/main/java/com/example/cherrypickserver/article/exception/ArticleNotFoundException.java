package com.example.cherrypickserver.article.exception;

public class ArticleNotFoundException extends RuntimeException{
  public ArticleNotFoundException(){super("요청하신 idx에 해당하는 기사가 존재하지 않습니다.");}
}
