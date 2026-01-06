package com.eduai.controller;

import com.eduai.service.AdaptiveLearningService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai/adaptive-learning")
@RequiredArgsConstructor
public class AdaptiveLearningController {

    private final AdaptiveLearningService adaptiveLearningService;

    @PostMapping("/learning-path/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> generateLearningPath(
            @PathVariable Long studentId,
            @RequestBody Map<String, Object> performanceData) {
        return adaptiveLearningService.generateLearningPath(studentId, performanceData)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/recommend-content/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> recommendContent(
            @PathVariable Long studentId,
            @RequestBody ContentRecommendationRequest request) {
        return adaptiveLearningService.recommendContent(
                studentId, request.getCurrentTopic(), request.getMasteryLevel())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/analyze-learning-style")
    public Mono<ResponseEntity<Map<String, Object>>> analyzeLearningStyle(
            @RequestBody Map<String, Object> studentData) {
        return adaptiveLearningService.analyzeLearningStyle(studentData)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @Data
    static class ContentRecommendationRequest {
        private String currentTopic;
        private String masteryLevel;
    }
}

