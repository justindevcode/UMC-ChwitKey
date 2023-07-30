package com.example.cherrypickserver.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {

    private Long chatId;

    private String greeting;
}
