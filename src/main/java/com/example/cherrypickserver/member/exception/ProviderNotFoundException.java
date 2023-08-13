package com.example.cherrypickserver.member.exception;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException() {
      super("Provider를 찾을 수 없습니다.");
    }
}
