package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.chat.dto.GptRequest;
import com.example.cherrypickserver.chat.dto.GptResponse;

public interface ChatService {
    GptResponse chatCompletion(GptRequest gptRequest);

    GptResponse chatSelect(Long articleId, String selectType);
}
