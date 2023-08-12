package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.chat.dto.request.ChatRequest;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.example.cherrypickserver.chat.dto.request.QuestionRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;

public interface ChatService {
    ChatResponse createChatAndContent(ChatRequest chatRequest);

    GptResponse chatQuestion(QuestionRequest questionRequest);

    GptResponse chatSelect(Long articleId, String selectType);
}
