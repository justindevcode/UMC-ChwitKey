package com.example.cherrypickserver.chat.exception;

public class ChatSelectNotFoundException extends RuntimeException {
    public ChatSelectNotFoundException() {
        super("선택 요청에 대한 답변을 찾을 수 없습니다.");
    }
}
