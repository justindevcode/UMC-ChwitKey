package com.example.cherrypickserver.member.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSignUpReq {

  private String nickname;
  private String gender;
  private String birth;
  private List<IndustryKeywordReq> industryKeywords;
}
