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
public class AdaptiveLearningService {

    private final GroqAIService groqAIService;

    public Mono<Map<String, Object>> generateLearningPath(Long studentId, Map<String, Object> performanceData) {
        String prompt = String.format(
            "Analyze the following student performance data and create a personalized learning path:\n\n" +
            "Student ID: %d\n" +
            "Performance Data: %s\n\n" +
            "Provide:\n" +
            "1. Learning style assessment\n" +
            "2. Recommended difficulty level\n" +
            "3. Next topics to focus on\n" +
            "4. Remediation areas\n" +
            "5. Study schedule suggestions\n\n" +
            "Respond in JSON format with keys: learningStyle, difficultyLevel, nextTopics (array), remediationAreas (array), studySchedule (object).",
            studentId, performanceData
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile")
                .doOnNext(result -> log.info("Generated learning path for student {}", studentId));
    }

    public Mono<Map<String, Object>> recommendContent(Long studentId, String currentTopic, String masteryLevel) {
        String prompt = String.format(
            "Student %d is currently studying '%s' with mastery level '%s'. " +
            "Recommend appropriate content including:\n" +
            "- Next topics based on current mastery\n" +
            "- Challenging material when ready\n" +
            "- Easier alternatives if struggling\n" +
            "- Peer study group suggestions\n\n" +
            "Respond in JSON format with: nextTopics, challengingContent, alternativeContent, studyGroupRecommendations.",
            studentId, currentTopic, masteryLevel
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }

    public Mono<Map<String, Object>> analyzeLearningStyle(Map<String, Object> studentData) {
        String prompt = String.format(
            "Analyze the learning data and determine the student's learning style:\n%s\n\n" +
            "Determine if the student is primarily visual, auditory, or kinesthetic learner. " +
            "Provide recommendations for content delivery. " +
            "Respond in JSON with: primaryStyle, secondaryStyle, recommendations (array).",
            studentData
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }
}

