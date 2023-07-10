package com.example.cherrypickserver.member.exception;

public class NotValidBirthdateException extends RuntimeException {

    public NotValidBirthdateException(String message) {
        super(message);
    }

    public NotValidBirthdateException() {
        this("유효하지 않은 생년월일 입니다.");
    }
}
