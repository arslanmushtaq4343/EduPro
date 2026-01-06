package com.eduai.repository;

import com.eduai.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByUserId(Long userId);
    List<Student> findByGradeLevel(String gradeLevel);
    boolean existsByStudentId(String studentId);
}

