package com.example.cherrypickserver.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "채팅방 생성 응답 DTO")
@Getter
@Builder
public class ChatResponse {

    @Schema(description = "채팅방 아이디")
    private Long chatId;

    @Schema(description = "채팅방 시작 인삿말")
    private String greeting;
}
