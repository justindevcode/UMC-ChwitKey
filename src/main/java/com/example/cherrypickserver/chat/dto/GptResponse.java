package com.example.cherrypickserver.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GptResponse {

    private String answer;
}
