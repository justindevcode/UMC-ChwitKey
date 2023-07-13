package com.example.cherrypickserver.chat.presentation;

import com.example.cherrypickserver.chat.application.ChatService;
import com.example.cherrypickserver.chat.dto.GptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/")
    public ResponseEntity<?> chatCompletion(@RequestBody GptRequest gptRequest) {
        return chatService.chatCompletion(gptRequest);
    }
}
