package com.example.cherrypickserver.chat.presentation;

import com.example.cherrypickserver.chat.application.ChatService;
import com.example.cherrypickserver.chat.dto.GptRequest;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/qna")
    public ResponseCustom<GptResponse> chatCompletion(@RequestBody GptRequest gptRequest) {
        return ResponseCustom.OK(chatService.chatCompletion(gptRequest));
    }

    @GetMapping("/summary/{articleId}")
    public ResponseCustom<GptResponse> chatSummary(@PathVariable Long articleId) {
        return ResponseCustom.OK(chatService.chatSummary(articleId));
    }
}
