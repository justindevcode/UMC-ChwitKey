package com.example.cherrypickserver.member.dto.request;

import lombok.Getter;

@Getter
public class PostSignUpReq {

  private String nickname;
  private String gender;
  private String birth;
}
