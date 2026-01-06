package com.eduai.controller;

import com.eduai.model.Student;
import com.eduai.repository.StudentRepository;
import com.eduai.service.StudentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/grade/{gradeLevel}")
    public ResponseEntity<List<Student>> getStudentsByGrade(@PathVariable String gradeLevel) {
        return ResponseEntity.ok(studentRepository.findByGradeLevel(gradeLevel));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentCreateRequest request) {
        try {
            Student student = studentService.createStudent(
                    request.getUserId(),
                    request.getStudentId(),
                    request.getDateOfBirth(),
                    request.getGradeLevel(),
                    request.getEmergencyContactName(),
                    request.getEmergencyContactPhone()
            );
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updated = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Data
    static class StudentCreateRequest {
        private Long userId;
        private String studentId;
        private LocalDate dateOfBirth;
        private String gradeLevel;
        private String emergencyContactName;
        private String emergencyContactPhone;
    }
}

