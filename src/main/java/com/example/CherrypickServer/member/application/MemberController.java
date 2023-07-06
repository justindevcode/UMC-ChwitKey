package com.example.CherrypickServer.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class MemberController {

  @ResponseBody
  @GetMapping("/test")
  public ResponseEntity<?> testController() {
    return ResponseEntity.ok("test");
  }
}
