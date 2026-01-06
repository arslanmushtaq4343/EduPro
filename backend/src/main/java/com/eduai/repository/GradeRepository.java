package com.eduai.repository;

import com.eduai.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByAssignmentId(Long assignmentId);
    Optional<Grade> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);
    List<Grade> findByStudentIdAndAssignmentIdIn(Long studentId, List<Long> assignmentIds);
}

