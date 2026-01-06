package com.eduai.service;

import com.eduai.model.Assignment;
import com.eduai.model.Grade;
import com.eduai.repository.AssignmentRepository;
import com.eduai.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final GradeRepository gradeRepository;
    private final AssignmentRepository assignmentRepository;

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Grade submitGrade(Grade grade) {
        grade.setSubmittedAt(LocalDateTime.now());
        
        Assignment assignment = assignmentRepository.findById(grade.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        
        if (assignment.getDueDate() != null && grade.getSubmittedAt().isAfter(assignment.getDueDate())) {
            grade.setIsLate(true);
        }
        
        if (grade.getPointsEarned() != null && assignment.getMaxPoints() != null) {
            grade.setPercentage((grade.getPointsEarned() / assignment.getMaxPoints()) * 100);
            grade.setLetterGrade(calculateLetterGrade(grade.getPercentage()));
        }
        
        return gradeRepository.save(grade);
    }

    public Grade gradeAssignment(Long gradeId, Double pointsEarned, String feedback) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        
        grade.setPointsEarned(pointsEarned);
        grade.setFeedback(feedback);
        grade.setGradedAt(LocalDateTime.now());
        
        Assignment assignment = assignmentRepository.findById(grade.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        
        if (assignment.getMaxPoints() != null) {
            grade.setPercentage((pointsEarned / assignment.getMaxPoints()) * 100);
            grade.setLetterGrade(calculateLetterGrade(grade.getPercentage()));
        }
        
        return gradeRepository.save(grade);
    }

    public Map<String, Object> calculateStudentGrade(Long studentId, Long courseId) {
        List<Assignment> assignments = assignmentRepository.findByCourseId(courseId);
        List<Long> assignmentIds = assignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toList());
        
        List<Grade> grades = gradeRepository.findByStudentIdAndAssignmentIdIn(studentId, assignmentIds);
        
        double totalWeightedPoints = 0.0;
        double totalWeight = 0.0;
        
        for (Assignment assignment : assignments) {
            Optional<Grade> gradeOpt = grades.stream()
                    .filter(g -> g.getAssignmentId().equals(assignment.getId()))
                    .findFirst();
            
            if (gradeOpt.isPresent() && gradeOpt.get().getPercentage() != null) {
                double weight = assignment.getWeightPercentage() != null ? assignment.getWeightPercentage() : 1.0;
                totalWeightedPoints += gradeOpt.get().getPercentage() * weight;
                totalWeight += weight;
            } else if (assignment.getWeightPercentage() != null) {
                totalWeight += assignment.getWeightPercentage();
            }
        }
        
        double finalPercentage = totalWeight > 0 ? totalWeightedPoints / totalWeight : 0.0;
        
        return Map.of(
                "studentId", studentId,
                "courseId", courseId,
                "finalPercentage", finalPercentage,
                "letterGrade", calculateLetterGrade(finalPercentage),
                "assignmentsCompleted", grades.size(),
                "assignmentsTotal", assignments.size()
        );
    }

    private String calculateLetterGrade(Double percentage) {
        if (percentage >= 97) return "A+";
        if (percentage >= 93) return "A";
        if (percentage >= 90) return "A-";
        if (percentage >= 87) return "B+";
        if (percentage >= 83) return "B";
        if (percentage >= 80) return "B-";
        if (percentage >= 77) return "C+";
        if (percentage >= 73) return "C";
        if (percentage >= 70) return "C-";
        if (percentage >= 67) return "D+";
        if (percentage >= 63) return "D";
        if (percentage >= 60) return "D-";
        return "F";
    }

    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }
}

