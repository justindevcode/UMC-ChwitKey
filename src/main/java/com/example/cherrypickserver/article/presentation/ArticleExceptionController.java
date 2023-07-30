package com.example.cherrypickserver.article.presentation;

import com.example.cherrypickserver.article.exception.*;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@Slf4j
@RestControllerAdvice
public class ArticleExceptionController {

  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseCustom<?> catchArticleNotFoundException(ArticleNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }

  @ExceptionHandler(ParseException.class)
  public ResponseCustom<?> catchParseException(ParseException e) {
    log.error(e.getMessage());
    return ResponseCustom.BAD_REQUEST(e.getMessage());
  }

  @ExceptionHandler(AttentionTypeNotFoundException.class)
  public ResponseCustom<?> catchAttentionTypeNotFoundException(AttentionTypeNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }

  @ExceptionHandler(AlreadyAttendArticleException.class)
  public ResponseCustom<?> catchAlreadyAttendArticleException(AlreadyAttendArticleException e) {
    log.error(e.getMessage());
    return ResponseCustom.BAD_REQUEST(e.getMessage());
  }

  @ExceptionHandler(ArticleAttentionNotFoundException.class)
  public ResponseCustom<?> catchArticleAttentionNotFoundException(ArticleAttentionNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.BAD_REQUEST(e.getMessage());
  }

  @ExceptionHandler(SortTypeNotFoundException.class)
  public ResponseCustom<?> catchSortTypeNotFoundException(SortTypeNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }

  @ExceptionHandler(IndustryNotFoundException.class)
  public ResponseCustom<?> catchIndustryNotFoundException(IndustryNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }
}

