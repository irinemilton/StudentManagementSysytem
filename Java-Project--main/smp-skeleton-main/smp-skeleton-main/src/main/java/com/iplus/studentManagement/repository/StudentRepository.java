package com.iplus.studentManagement.repository;

import com.iplus.studentManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Custom method to find a student by their unique email
    Student findByEmail(String email);
}