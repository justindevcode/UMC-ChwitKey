package com.example.cherrypickserver.chat.presentation;

import com.example.cherrypickserver.chat.exception.ChatNotFoundException;
import com.example.cherrypickserver.chat.exception.ChatSelectNotFoundException;
import com.example.cherrypickserver.chat.exception.SelectTypeNotFoundException;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ChatExceptionController {

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseCustom<String> catchChatNotFoundException(ChatNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(ChatSelectNotFoundException.class)
    public ResponseCustom<String> catchChatSelectNotFoundException(ChatSelectNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(SelectTypeNotFoundException.class)
    public ResponseCustom<String> catchSelectTypeNotFoundException(SelectTypeNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }
}
