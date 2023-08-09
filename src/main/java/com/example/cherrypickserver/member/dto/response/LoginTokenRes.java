package com.example.cherrypickserver.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginTokenRes {

  private String access_token;
  private String refresh_token;
  private Boolean isMember;

  @Builder
  public LoginTokenRes(String access_token, String refresh_token, Boolean isMember) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
    this.isMember = isMember;
  }

  public static LoginTokenRes toDto(String token, Boolean isMember) {
    return LoginTokenRes.builder()
            .access_token(token.split(",")[0])
            .refresh_token(token.split(",")[1])
            .isMember(isMember)
            .build();
  }
}
