package com.eduai.controller;

import com.eduai.service.AITeachingAssistantService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai/teaching-assistant")
@RequiredArgsConstructor
public class AITeachingAssistantController {

    private final AITeachingAssistantService aiTeachingAssistantService;

    @PostMapping("/lesson-plan")
    public Mono<ResponseEntity<Map<String, Object>>> generateLessonPlan(
            @RequestBody LessonPlanRequest request) {
        return aiTeachingAssistantService.generateLessonPlan(
                request.getTopic(),
                request.getGradeLevel(),
                request.getDuration(),
                request.getLearningObjectives())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/assessment")
    public Mono<ResponseEntity<Map<String, Object>>> generateAssessment(
            @RequestBody AssessmentRequest request) {
        return aiTeachingAssistantService.generateAssessment(
                request.getTopic(),
                request.getGradeLevel(),
                request.getNumQuestions(),
                request.getDifficultyMix())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/auto-grade")
    public Mono<ResponseEntity<Map<String, Object>>> autoGradeAnswer(
            @RequestBody GradingRequest request) {
        return aiTeachingAssistantService.autoGradeAnswer(
                request.getQuestion(),
                request.getStudentAnswer(),
                request.getRubric())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/intervention-plan/{studentId}")
    public Mono<ResponseEntity<Map<String, Object>>> generateInterventionPlan(
            @PathVariable Long studentId,
            @RequestBody InterventionRequest request) {
        return aiTeachingAssistantService.generateInterventionPlan(
                studentId,
                request.getIssue(),
                request.getContext())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @Data
    static class LessonPlanRequest {
        private String topic;
        private String gradeLevel;
        private int duration;
        private String learningObjectives;
    }

    @Data
    static class AssessmentRequest {
        private String topic;
        private String gradeLevel;
        private int numQuestions;
        private String difficultyMix;
    }

    @Data
    static class GradingRequest {
        private String question;
        private String studentAnswer;
        private String rubric;
    }

    @Data
    static class InterventionRequest {
        private String issue;
        private Map<String, Object> context;
    }
}

