package com.iplus.studentManagement.service;

import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Dependency Injection (Spring injects the repository)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // --- Core CRUD Methods ---

    // Retrieves all students for listing on studetns.html
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Saves a new student or updates an existing one
    public Student saveStudent(Student student) {
        // Validation Logic: Check if email already exists for a *different* student
        Student existingStudent = studentRepository.findByEmail(student.getEmail());
        
        // If a student exists with this email AND it's not the same student being updated
        if (existingStudent != null && (student.getId() == null || !existingStudent.getId().equals(student.getId()))) {
            // Throw an exception that the Controller will catch to display an error message
            throw new IllegalStateException("Email already registered: " + student.getEmail());
        }
        return studentRepository.save(student);
    }
    
    // Retrieves a student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Deletes a student by ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}