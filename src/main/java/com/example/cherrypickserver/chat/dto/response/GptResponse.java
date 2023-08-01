package com.example.cherrypickserver.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GptResponse {

    private String answer;
}
