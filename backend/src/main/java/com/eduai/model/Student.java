package com.eduai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    
    @Column(name = "grade_level")
    private String gradeLevel;
    
    @Column(name = "gpa")
    private Double gpa;
    
    @Column(name = "class_rank")
    private Integer classRank;
    
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Column(name = "medical_conditions", columnDefinition = "TEXT")
    private String medicalConditions;
    
    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;
    
    @Column(name = "iep_tracking")
    private Boolean iepTracking = false;
    
    @Column(name = "learning_disabilities", columnDefinition = "TEXT")
    private String learningDisabilities;
    
    @ElementCollection
    @CollectionTable(name = "student_parents", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "parent_id")
    private List<Long> parentIds = new ArrayList<>();
}

