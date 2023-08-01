package com.example.cherrypickserver.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "질문 요청 DTO")
@Getter
public class QuestionRequest {

    @Schema(description = "채팅방 아이디")
    private Long chatId;

    @Schema(description = "질문 내용")
    private String question;
}
