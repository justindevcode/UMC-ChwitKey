package com.example.cherrypickserver.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Chat GPT 답변 응답 DTO")
@Getter
@Builder
public class GptResponse {

    @Schema(description = "답변 내용")
    private String answer;
}
