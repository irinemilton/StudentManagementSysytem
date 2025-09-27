package com.iplus.studentManagement.repository;

import com.iplus.studentManagement.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    // Custom method to fetch all enrollments for a specific student ID
    List<Enrollment> findByStudentId(Long studentId);
}