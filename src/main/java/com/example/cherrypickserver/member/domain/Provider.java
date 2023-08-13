package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.member.exception.ProviderNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Provider {

  KAKAO("kakao"),
  NAVER("naver");

  private final String name;

  public static Provider getProviderByName(String name){
    return Arrays.stream(Provider.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElseThrow(ProviderNotFoundException::new);

  }
}
