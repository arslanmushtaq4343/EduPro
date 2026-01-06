package com.eduai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Data
@EqualsAndHashCode(callSuper = true)
public class Grade extends BaseEntity {
    
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;
    
    @Column(name = "points_earned")
    private Double pointsEarned;
    
    @Column(name = "percentage")
    private Double percentage;
    
    @Column(name = "letter_grade")
    private String letterGrade;
    
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @Column(name = "graded_at")
    private LocalDateTime gradedAt;
    
    @Column(name = "is_late")
    private Boolean isLate = false;
    
    @Column(name = "late_penalty")
    private Double latePenalty;
}

