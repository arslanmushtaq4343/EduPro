package com.eduai.controller;

import com.eduai.service.PredictiveAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai/analytics")
@RequiredArgsConstructor
public class PredictiveAnalyticsController {

    private final PredictiveAnalyticsService predictiveAnalyticsService;

    @PostMapping("/risk-score/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> calculateRiskScore(
            @PathVariable Long studentId,
            @RequestBody Map<String, Object> metrics) {
        return predictiveAnalyticsService.calculateRiskScore(studentId, metrics)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/predict-grade/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> predictFinalGrade(
            @PathVariable Long studentId,
            @RequestBody Map<String, Object> currentData) {
        return predictiveAnalyticsService.predictFinalGrade(studentId, currentData)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/early-warning/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> generateEarlyWarning(
            @PathVariable Long studentId,
            @RequestBody Map<String, Object> indicators) {
        return predictiveAnalyticsService.generateEarlyWarning(studentId, indicators)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/class-performance")
    public Mono<ResponseEntity<Map<String, Object>>> analyzeClassPerformance(
            @RequestBody Map<String, Object> classData) {
        return predictiveAnalyticsService.analyzeClassPerformance(classData)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
}

