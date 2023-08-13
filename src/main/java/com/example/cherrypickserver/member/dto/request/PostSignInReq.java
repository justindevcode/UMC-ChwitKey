package com.example.cherrypickserver.member.dto.request;

import lombok.Getter;

@Getter
public class PostSignInReq {
  private String memberNumber;
  private String provider;
}
