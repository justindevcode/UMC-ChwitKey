package com.example.cherrypickserver.chat.exception;

public class SelectTypeNotFoundException extends RuntimeException {
    public SelectTypeNotFoundException() {
        super("해당하는 Select Type을 찾을 수 없습니다.");
    }
}
