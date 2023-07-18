package com.example.cherrypickserver.chat.dto;

import lombok.Getter;

@Getter
public class GptRequest {

    private Long memberId;

    private Long articleId;

    private String question;
}
