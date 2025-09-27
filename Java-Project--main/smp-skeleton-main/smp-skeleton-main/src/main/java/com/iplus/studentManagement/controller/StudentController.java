package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation; // <-- ADD THIS IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/students")
@Tag(name = "Students", description = "Management of student records and unique email validation.")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /students - List all students (Maps to studetns.html)
    @Operation(summary = "View List of Students", description = "Retrieves the student list view (GET). Accessible to ROLE_USER and ROLE_ADMIN.")
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "studetns"; 
    }

    // GET /students/new - Show new student form (Maps to create_student.html)
    @Operation(summary = "Show Create Student Form", description = "Displays the form to add a new student. Accessible only to ROLE_ADMIN.")
    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student";
    }

    // POST /students - Save new student
    @Operation(summary = "Create New Student", description = "Saves a new student record to the database (POST). Accessible only to ROLE_ADMIN.")
    @PostMapping
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student added successfully!");
            return "redirect:/students";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/new"; 
        }
    }

    // GET /students/edit/{id} - Show edit form (Maps to edit_student.html)
    @Operation(summary = "Show Edit Student Form", description = "Displays the form to edit an existing student by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "edit_student";
    }

    // POST /students/{id} - Update existing student
    @Operation(summary = "Update Student", description = "Updates an existing student record (POST to ID). Accessible only to ROLE_ADMIN.")
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student studentDetails, RedirectAttributes redirectAttributes) {
        Student existingStudent = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));

        existingStudent.setFirstName(studentDetails.getFirstName());
        existingStudent.setLastName(studentDetails.getLastName());
        existingStudent.setEmail(studentDetails.getEmail());

        try {
            studentService.saveStudent(existingStudent);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
            return "redirect:/students";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/edit/" + id;
        }
    }

    // GET /students/delete/{id} - Delete a student
    @Operation(summary = "Delete Student", description = "Deletes a student by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/students";
    }
}