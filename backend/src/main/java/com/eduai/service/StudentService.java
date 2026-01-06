package com.eduai.service;

import com.eduai.model.Student;
import com.eduai.model.User;
import com.eduai.model.enums.UserRole;
import com.eduai.repository.StudentRepository;
import com.eduai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Student createStudent(Long userId, String studentId, LocalDate dateOfBirth,
                                  String gradeLevel, String emergencyContactName,
                                  String emergencyContactPhone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(UserRole.STUDENT)) {
            throw new RuntimeException("User role must be STUDENT");
        }

        if (studentRepository.existsByStudentId(studentId)) {
            throw new RuntimeException("Student ID already exists");
        }

        Student student = new Student();
        student.setUser(user);
        student.setStudentId(studentId);
        student.setDateOfBirth(dateOfBirth);
        student.setEnrollmentDate(LocalDate.now());
        student.setGradeLevel(gradeLevel);
        student.setEmergencyContactName(emergencyContactName);
        student.setEmergencyContactPhone(emergencyContactPhone);

        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    @Transactional
    public Student updateStudent(Long id, Student updatedData) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (updatedData.getGradeLevel() != null) {
            student.setGradeLevel(updatedData.getGradeLevel());
        }
        if (updatedData.getGpa() != null) {
            student.setGpa(updatedData.getGpa());
        }
        if (updatedData.getMedicalConditions() != null) {
            student.setMedicalConditions(updatedData.getMedicalConditions());
        }
        if (updatedData.getAllergies() != null) {
            student.setAllergies(updatedData.getAllergies());
        }

        return studentRepository.save(student);
    }
}

