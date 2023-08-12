package com.example.cherrypickserver.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "채팅방 생성 요청 DTO")
@Getter
public class ChatRequest {

    @Schema(description = "회원 아이디")
    private String memberNumber;

    @Schema(description = "기사 아이디")
    private Long articleId;
}
