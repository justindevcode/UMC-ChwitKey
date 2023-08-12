package com.example.cherrypickserver.global.config;

import com.example.cherrypickserver.global.resolver.LoginResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

//  private final LoginResolver isLoginResolver;
//
//  @Override
//  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//    resolvers.add(isLoginResolver);
//  }

  private final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000", "http://127.0.0.1:5173","http://localhost:5173")
        .allowedOrigins("**")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(MAX_AGE_SECS);
  }

}
