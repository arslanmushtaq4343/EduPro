package com.eduai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GroqAIService {

    private final WebClient groqWebClient;
    private final ObjectMapper objectMapper;
    
    @Value("${groq.api.model}")
    private String defaultModel;

    public GroqAIService(WebClient groqWebClient, ObjectMapper objectMapper) {
        this.groqWebClient = groqWebClient;
        this.objectMapper = objectMapper;
    }

    public Mono<String> generateText(String prompt) {
        return generateText(prompt, defaultModel);
    }

    public Mono<String> generateText(String prompt, String model) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", new Object[]{
            Map.of("role", "user", "content", prompt)
        });
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 2000);

        return groqWebClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30))
                .map(response -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response);
                        return jsonNode.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();
                    } catch (Exception e) {
                        log.error("Error parsing Groq API response", e);
                        return "Error processing AI response";
                    }
                })
                .onErrorResume(error -> {
                    log.error("Error calling Groq API", error);
                    return Mono.just("AI service temporarily unavailable");
                });
    }

    public Mono<Map<String, Object>> generateStructuredResponse(String prompt, String model) {
        String structuredPrompt = prompt + "\n\nPlease respond in valid JSON format.";
        
        return generateText(structuredPrompt, model)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, Map.class);
                    } catch (Exception e) {
                        log.error("Error parsing structured response", e);
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Failed to parse structured response");
                        errorResponse.put("raw_response", response);
                        return errorResponse;
                    }
                });
    }
}

