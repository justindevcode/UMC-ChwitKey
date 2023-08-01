package com.example.cherrypickserver.chat.dto.request;

import lombok.Getter;

@Getter
public class QuestionRequest {

    private Long chatId;

    private String question;
}
