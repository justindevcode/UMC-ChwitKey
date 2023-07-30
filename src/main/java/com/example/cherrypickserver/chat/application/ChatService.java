package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.chat.dto.GptRequest;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.example.cherrypickserver.chat.dto.request.ChatRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;

public interface ChatService {
    ChatResponse createChatAndContent(ChatRequest chatRequest);

    GptResponse chatQuestion(GptRequest gptRequest);

    GptResponse chatSelect(Long articleId, String selectType);
}
