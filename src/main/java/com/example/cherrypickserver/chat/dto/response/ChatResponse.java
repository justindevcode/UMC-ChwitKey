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

    @Schema(description = "채팅방 시작 인삿말",
            example = "안녕하세요. 취트키GPT입니다.\n'기사 제목' 기사를 보고 오셨네요.\n"
            + "기사 내용 중 궁금한 부분이 있다면 물어보세요.")
    private String greeting;
}
