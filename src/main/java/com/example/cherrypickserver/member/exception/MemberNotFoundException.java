package com.example.cherrypickserver.member.exception;

public class MemberNotFoundException extends RuntimeException{
  public MemberNotFoundException(){super("요청하신 idx에 해당하는 회원이 존재하지 않습니다.");}
}
