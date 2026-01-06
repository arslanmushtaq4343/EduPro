package com.eduai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GroqApiConfig {

    @Value("${groq.api.base-url}")
    private String groqBaseUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Bean
    public WebClient groqWebClient() {
        return WebClient.builder()
                .baseUrl(groqBaseUrl)
                .defaultHeader("Authorization", "Bearer " + groqApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}

