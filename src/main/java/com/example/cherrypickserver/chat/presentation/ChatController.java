package com.example.cherrypickserver.chat.presentation;

import com.example.cherrypickserver.chat.application.ChatService;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.example.cherrypickserver.chat.dto.request.ChatRequest;
import com.example.cherrypickserver.chat.dto.request.QuestionRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/")
    public ResponseCustom<ChatResponse> createChatAndContent(@RequestBody ChatRequest chatRequest) {
        return ResponseCustom.OK(chatService.createChatAndContent(chatRequest));
    }

    @PostMapping("/qna")
    public ResponseCustom<GptResponse> chatQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseCustom.OK(chatService.chatQuestion(questionRequest));
    }

    @GetMapping("/select/{articleId}")
    public ResponseCustom<GptResponse> chatSelect(@PathVariable Long articleId, @RequestParam String type) {
        return ResponseCustom.OK(chatService.chatSelect(articleId, type));
    }
}
