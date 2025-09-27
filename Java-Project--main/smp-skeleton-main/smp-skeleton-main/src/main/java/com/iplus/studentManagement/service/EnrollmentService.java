package com.iplus.studentManagement.service;

import com.iplus.studentManagement.entity.Enrollment;
import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.entity.Course;
import com.iplus.studentManagement.repository.EnrollmentRepository;
import com.iplus.studentManagement.repository.StudentRepository;
import com.iplus.studentManagement.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // --- Core CRUD Methods ---

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    // Handles the complex logic of creating/updating enrollment using IDs
    public Enrollment saveEnrollment(Long studentId, Long courseId, String grade, Long enrollmentId) {
        
        // Fetch Student and Course Entities
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Student ID: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Course ID: " + courseId));

        Enrollment enrollment;
        if (enrollmentId == null) {
            // New Enrollment
            enrollment = new Enrollment();
        } else {
            // Existing Enrollment (Update)
            enrollment = enrollmentRepository.findById(enrollmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Enrollment ID: " + enrollmentId));
        }

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(grade);
        
        return enrollmentRepository.save(enrollment);
    }
    
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
    
    // --- Specialized Queries ---
    
    // Used by grades.html and student_enrollments.html
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}