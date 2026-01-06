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
public class AITeachingAssistantService {

    private final GroqAIService groqAIService;

    public Mono<Map<String, Object>> generateLessonPlan(String topic, String gradeLevel, int duration, String learningObjectives) {
        String prompt = String.format(
            "Create a comprehensive lesson plan:\n" +
            "Topic: %s\n" +
            "Grade Level: %s\n" +
            "Duration: %d minutes\n" +
            "Learning Objectives: %s\n\n" +
            "Include:\n" +
            "1. Introduction hook activities\n" +
            "2. Core instruction breakdown (5-10 min segments)\n" +
            "3. Interactive activities with instructions\n" +
            "4. Assessment checkpoints\n" +
            "5. Differentiation strategies\n" +
            "6. Materials list\n" +
            "7. Homework assignments\n" +
            "8. Extension activities\n\n" +
            "Respond in JSON format with all sections structured.",
            topic, gradeLevel, duration, learningObjectives
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile")
                .doOnNext(result -> log.info("Generated lesson plan for topic: {}", topic));
    }

    public Mono<Map<String, Object>> generateAssessment(String topic, String gradeLevel, int numQuestions, String difficultyMix) {
        String prompt = String.format(
            "Generate assessment questions:\n" +
            "Topic: %s\n" +
            "Grade Level: %s\n" +
            "Number of Questions: %d\n" +
            "Difficulty Mix: %s\n\n" +
            "Create mix of question types:\n" +
            "- Multiple choice\n" +
            "- Short answer\n" +
            "- Essay questions\n\n" +
            "Align with Bloom's Taxonomy. " +
            "Respond in JSON: questions (array with type, question, options, answer, rubric, difficulty).",
            topic, gradeLevel, numQuestions, difficultyMix
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }

    public Mono<Map<String, Object>> autoGradeAnswer(String question, String studentAnswer, String rubric) {
        String prompt = String.format(
            "Grade the student answer:\n" +
            "Question: %s\n" +
            "Student Answer: %s\n" +
            "Rubric: %s\n\n" +
            "Provide: score (0-100), feedback, strengths (array), improvements (array), " +
            "partialCreditBreakdown. Use semantic analysis, not keyword matching.",
            question, studentAnswer, rubric
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }

    public Mono<Map<String, Object>> generateInterventionPlan(Long studentId, String issue, Map<String, Object> context) {
        String prompt = String.format(
            "Create intervention plan:\n" +
            "Student ID: %d\n" +
            "Issue: %s\n" +
            "Context: %s\n\n" +
            "Provide: actionSteps (array), resources, timeline, expectedOutcomes, " +
            "successMetrics, parentInvolvement (boolean).",
            studentId, issue, context
        );

        return groqAIService.generateStructuredResponse(prompt, "llama-3.1-70b-versatile");
    }
}

