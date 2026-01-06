package com.eduai.model;

import com.eduai.model.enums.AssignmentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Data
@EqualsAndHashCode(callSuper = true)
public class Assignment extends BaseEntity {
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentType type;
    
    @Column(name = "course_id")
    private Long courseId;
    
    @Column(name = "teacher_id")
    private Long teacherId;
    
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    
    @Column(name = "max_points")
    private Double maxPoints;
    
    @Column(name = "weight_percentage")
    private Double weightPercentage;
    
    @Column(name = "is_published")
    private Boolean isPublished = false;
    
    @Column(name = "rubric", columnDefinition = "TEXT")
    private String rubric;
}

