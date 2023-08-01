package com.example.cherrypickserver.chat.presentation;

import com.example.cherrypickserver.chat.application.ChatService;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.example.cherrypickserver.chat.dto.request.ChatRequest;
import com.example.cherrypickserver.chat.dto.request.QuestionRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;
import com.example.cherrypickserver.global.dto.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chat Controller", description = "Chat GPT 관련 컨트롤러")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "채팅방 생성", description = "새로운 채팅방 생성을 요청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채팅방 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 기사")
    })
    @PostMapping("/")
    public ResponseCustom<ChatResponse> createChatAndContent(@RequestBody ChatRequest chatRequest) {
        return ResponseCustom.OK(chatService.createChatAndContent(chatRequest));
    }

    @Operation(summary = "질문 기능", description = "질문에 대한 답변을 요청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "답변 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 채팅방")
    })
    @PostMapping("/qna")
    public ResponseCustom<GptResponse> chatQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseCustom.OK(chatService.chatQuestion(questionRequest));
    }

    @Operation(summary = "선택 기능",
            description = "버튼으로 제시된 세 기능 중 하나를 요청합니다. "
                    + "기능은 type(summary, translation, keyword)에 따라 요약, 영어 번역, 중요 키워드 추출로 구분됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "답변 생성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 기사"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 기능")
    })
    @Parameters({
            @Parameter(name = "articleId", description = "기사 아이디"),
            @Parameter(name = "type", description = "summary(요약), translation(번역), keyword(키워드 추출)")
    })
    @GetMapping("/select/{articleId}")
    public ResponseCustom<GptResponse> chatSelect(@PathVariable Long articleId, @RequestParam String type) {
        return ResponseCustom.OK(chatService.chatSelect(articleId, type));
    }
}
