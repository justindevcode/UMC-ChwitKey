package com.example.cherrypickserver.chat.dto;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GptFullResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Message> messages;

    private Usage usage;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        private String role;

        private String message;

        //답변(ChatMessage) 중 답변 내용만 꺼내기
        public static Message of(ChatMessage chatMessage) {
            return new Message(
                    chatMessage.getRole(),
                    chatMessage.getContent()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {

        private Long promptTokens;

        private Long completionTokens;

        private Long totalTokens;

        //답변(Usage) 중 토큰 사용 내용 꺼내기
        public static Usage of(com.theokanning.openai.Usage usage) {
            return new Usage(
                    usage.getPromptTokens(),
                    usage.getCompletionTokens(),
                    usage.getTotalTokens()
            );
        }
    }

    //ChatCompletionChoice to Message
    public static List<Message> toResponseListBy(List<ChatCompletionChoice> choices) {
        return choices.stream()
                .map(completionChoice -> Message.of(completionChoice.getMessage()))
                .collect(Collectors.toList());
    }

    //ChatCompletionResult to GptResponse
    public static GptFullResponse of(ChatCompletionResult result) {
        return GptFullResponse.builder()
                .id(result.getId())
                .object(result.getObject())
                .created(result.getCreated())
                .model(result.getModel())
                .messages(toResponseListBy(result.getChoices()))
                .usage(Usage.of(result.getUsage()))
                .build();
    }
}
