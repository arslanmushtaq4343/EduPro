package com.eduai.controller;

import com.eduai.model.Assignment;
import com.eduai.model.Grade;
import com.eduai.repository.AssignmentRepository;
import com.eduai.service.GradebookService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gradebook")
@RequiredArgsConstructor
public class GradebookController {

    private final GradebookService gradebookService;
    private final AssignmentRepository assignmentRepository;

    @PostMapping("/assignments")
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            Assignment created = gradebookService.createAssignment(assignment);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/assignments/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(assignmentRepository.findByCourseId(courseId));
    }

    @PostMapping("/grades")
    public ResponseEntity<?> submitGrade(@RequestBody Grade grade) {
        try {
            Grade submitted = gradebookService.submitGrade(grade);
            return ResponseEntity.ok(submitted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/grades/{gradeId}/grade")
    public ResponseEntity<?> gradeAssignment(
            @PathVariable Long gradeId,
            @RequestBody GradeAssignmentRequest request) {
        try {
            Grade graded = gradebookService.gradeAssignment(
                    gradeId, request.getPointsEarned(), request.getFeedback());
            return ResponseEntity.ok(graded);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/students/{studentId}/grades")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradebookService.getStudentGrades(studentId));
    }

    @GetMapping("/students/{studentId}/courses/{courseId}/final-grade")
    public ResponseEntity<Map<String, Object>> calculateFinalGrade(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(gradebookService.calculateStudentGrade(studentId, courseId));
    }

    @Data
    static class GradeAssignmentRequest {
        private Double pointsEarned;
        private String feedback;
    }
}

