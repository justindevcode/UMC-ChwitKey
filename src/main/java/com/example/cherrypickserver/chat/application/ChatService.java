package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.example.cherrypickserver.chat.dto.request.QuestionRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;

public interface ChatService {
    ChatResponse createChatAndContent(Long memberId, Long articleId);

    GptResponse chatQuestion(QuestionRequest questionRequest);

    GptResponse chatSelect(Long articleId, String selectType);
}
