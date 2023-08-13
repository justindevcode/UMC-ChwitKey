package com.example.cherrypickserver.member.presentation;

import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import com.example.cherrypickserver.member.exception.ProviderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@Slf4j
@RestControllerAdvice
public class MemberExceptionController {

  @ExceptionHandler(MemberNotFoundException.class)
  public ResponseCustom<?> catchMemberNotFoundException(MemberNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }

  @ExceptionHandler(ProviderNotFoundException.class)
  public ResponseCustom<?> catchProviderNotFoundException(ProviderNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }
}

