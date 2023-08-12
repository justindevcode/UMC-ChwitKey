package com.example.cherrypickserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CherrypickServerApplication {

  public static void main(String[] args) {
    System.out.println("hello world!");
    SpringApplication.run(CherrypickServerApplication.class, args);
  }

}
