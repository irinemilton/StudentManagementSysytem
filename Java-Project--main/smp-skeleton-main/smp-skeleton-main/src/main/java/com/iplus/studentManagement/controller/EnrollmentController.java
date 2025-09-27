package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.entity.Enrollment;
import com.iplus.studentManagement.service.EnrollmentService;
import com.iplus.studentManagement.service.StudentService;
import com.iplus.studentManagement.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation; // <-- ADD THIS IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
@Tag(name = "Enrollments", description = "Management of student enrollment in courses and grade assignment.")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // GET /enrollments - List all enrollments (Maps to enrollments.html)
    @Operation(summary = "View List of Enrollments", description = "Retrieves the enrollment list view (GET). Accessible to ROLE_USER and ROLE_ADMIN.")
    @GetMapping
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        return "enrollments";
    }

    // GET /enrollments/new - Show new enrollment form (Maps to create_enrollment.html)
    @Operation(summary = "Show Create Enrollment Form", description = "Displays the form to add a new enrollment. Accessible only to ROLE_ADMIN.")
    @GetMapping("/new")
    public String createEnrollmentForm(@RequestParam(required = false) Long studentId, Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        if (studentId != null) {
            model.addAttribute("selectedStudentId", studentId);
        }
        return "create_enrollment";
    }

    // POST /enrollments - Save new enrollment
    @Operation(summary = "Create New Enrollment", description = "Saves a new enrollment record (POST). Accessible only to ROLE_ADMIN.")
    @PostMapping
    public String saveEnrollment(@RequestParam("studentId") Long studentId, 
                                 @RequestParam("courseId") Long courseId, 
                                 @RequestParam("grade") String grade,
                                 RedirectAttributes redirectAttributes) {
        
        try {
            enrollmentService.saveEnrollment(studentId, courseId, grade, null);
            redirectAttributes.addFlashAttribute("success", "Enrollment created successfully!");
        } catch (IllegalArgumentException e) {
             redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/enrollments";
    }
    
    // GET /enrollments/{id} - View enrollment details (Maps to enrollment_details.html)
    @Operation(summary = "View Enrollment Details", description = "Displays the details for a single enrollment by ID. Accessible to ROLE_USER and ROLE_ADMIN.")
    @GetMapping("/{id}")
    public String viewEnrollmentDetails(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid enrollment Id:" + id));
        model.addAttribute("enrollment", enrollment);
        return "enrollment_details";
    }

    // GET /enrollments/edit/{id} - Show edit form (Maps to edit_enrollment.html)
    @Operation(summary = "Show Edit Enrollment Form", description = "Displays the form to edit an existing enrollment by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/edit/{id}")
    public String editEnrollmentForm(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid enrollment Id:" + id));

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "edit_enrollment";
    }

    // POST /enrollments/update/{id} - Update existing enrollment
    @Operation(summary = "Update Enrollment", description = "Updates an existing enrollment record (POST to ID). Accessible only to ROLE_ADMIN.")
    @PostMapping("/update/{id}")
    public String updateEnrollment(@PathVariable Long id, 
                                 @RequestParam("studentId") Long studentId, 
                                 @RequestParam("courseId") Long courseId, 
                                 @RequestParam("grade") String grade,
                                 RedirectAttributes redirectAttributes) {
        
        try {
            enrollmentService.saveEnrollment(studentId, courseId, grade, id);
            redirectAttributes.addFlashAttribute("success", "Enrollment updated successfully!");
        } catch (IllegalArgumentException e) {
             redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/enrollments";
    }

    // GET /enrollments/delete/{id} - Delete an enrollment
    @Operation(summary = "Delete Enrollment", description = "Deletes an enrollment record by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enrollmentService.deleteEnrollment(id);
        redirectAttributes.addFlashAttribute("success", "Enrollment deleted successfully!");
        return "redirect:/enrollments";
    }
    
    // GET /enrollments/student/{studentId} - List enrollments for a specific student
    @Operation(summary = "View Student Enrollments", description = "Retrieves the enrollment list for a specific student ID. Used by Grades page via AJAX. Accessible to ROLE_USER and ROLE_ADMIN.")
    @GetMapping("/student/{studentId}")
    public String listStudentEnrollments(@PathVariable Long studentId, Model model) {
        model.addAttribute("student", studentService.getStudentById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + studentId)));
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsByStudentId(studentId));
        return "student_enrollments";
    }
}