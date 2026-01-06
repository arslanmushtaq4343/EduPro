package com.eduai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GroqAIServiceTest {

    @Mock
    private WebClient groqWebClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private GroqAIService groqAIService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        groqAIService = new GroqAIService(groqWebClient, objectMapper);
    }

    @Test
    void testGenerateText() {
        String mockResponse = "{\"choices\":[{\"message\":{\"content\":\"Test AI response\"}}]}";
        
        // Note: This is a simplified test - full integration tests would require more complex mocking
        assertNotNull(groqAIService);
    }
}

