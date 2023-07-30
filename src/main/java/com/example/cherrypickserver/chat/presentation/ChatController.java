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

    @GetMapping("/select/{articleId}")
    public ResponseCustom<GptResponse> chatSelect(@PathVariable Long articleId, @RequestParam String type) {
        return ResponseCustom.OK(chatService.chatSelect(articleId, type));
    }
}
