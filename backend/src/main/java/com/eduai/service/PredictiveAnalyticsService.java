package com.eduai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredictiveAnalyticsService {

    private final GroqAIService groqAIService;

    public Mono<Map<String, Object>> calculateRiskScore(Long studentId, Map<String, Object> metrics) {
        String prompt = String.format(
            "Calculate at-risk student score (0-100) based on:\n" +
            "Student ID: %d\n" +
            "Metrics: %s\n\n" +
            "Consider:\n" +
            "- Attendance patterns\n" +
            "- Assignment submission rates\n" +
            "- Grade trajectory\n" +
            "- Engagement metrics\n" +
            "- Quiz performance\n" +
            "- Social interaction\n\n" +
            "Respond with JSON: riskScore (0-100), riskLevel (low/medium/high/critical), " +
            "primaryConcerns (array), recommendedActions (array), predictedOutcome, interventionUrgency.",
            studentId, metrics
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile")
                .doOnNext(result -> {
                    log.info("Calculated risk score for student {}: {}", studentId, result.get("riskScore"));
                });
    }

    public Mono<Map<String, Object>> predictFinalGrade(Long studentId, Map<String, Object> currentData) {
        String prompt = String.format(
            "Predict final grade based on current performance:\n" +
            "Student ID: %d\n" +
            "Current Data: %s\n\n" +
            "Analyze trends and predict final grade. " +
            "Respond with JSON: predictedGrade, confidence (0-100), " +
            "requiredPerformance (what they need to achieve target), timelineProjection.",
            studentId, currentData
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }

    public Mono<Map<String, Object>> generateEarlyWarning(Long studentId, Map<String, Object> indicators) {
        String prompt = String.format(
            "Generate early warning report for student %d:\n" +
            "Indicators: %s\n\n" +
            "Identify problems before they escalate. " +
            "Provide: warningLevel, identifiedRisks (array), timeline (when issues will escalate), " +
            "interventionStrategies (array), parentNotification (boolean), counselorEscalation (boolean).",
            studentId, indicators
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }

    public Mono<Map<String, Object>> analyzeClassPerformance(Map<String, Object> classData) {
        String prompt = String.format(
            "Analyze class-level performance:\n%s\n\n" +
            "Provide: averageRiskScore, highRiskStudents (count), " +
            "classTrend (improving/declining/stable), cohortComparison, recommendations.",
            classData
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }
}

