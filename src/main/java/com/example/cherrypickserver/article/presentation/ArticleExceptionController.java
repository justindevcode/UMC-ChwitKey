package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ArticleExceptionController {

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseCustom<?> catchArticleNotFoundException(ArticleNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }
}

