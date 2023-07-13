package com.example.cherrypickserver.global.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class ChatConfig {

    @Value("${gpt.key}")
    private String key;

    @Bean
    public OpenAiService openAiService() {
        log.info("OpenAiService 생성");
        return new OpenAiService(key, Duration.ofSeconds(60));
    }
}
