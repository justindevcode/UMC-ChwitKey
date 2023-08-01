package com.example.cherrypickserver.chat.exception;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {
        super("id에 알맞은 채팅방을 찾을 수 없습니다.");
    }
}
