package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.entity.Course;
import com.iplus.studentManagement.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation; // <-- ADD THIS IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
@Tag(name = "Courses", description = "Management of course offerings and descriptions.")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // GET /courses - List all courses (Maps to courses.html)
    @Operation(summary = "View List of Courses", description = "Retrieves the course list view (GET). Accessible to ROLE_USER and ROLE_ADMIN.")
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    // GET /courses/new - Show new course form (Maps to create_course.html)
    @Operation(summary = "Show Create Course Form", description = "Displays the form to add a new course. Accessible only to ROLE_ADMIN.")
    @GetMapping("/new")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "create_course";
    }

    // POST /courses - Save new course
    @Operation(summary = "Create New Course", description = "Saves a new course record (POST). Accessible only to ROLE_ADMIN.")
    @PostMapping
    public String saveCourse(@ModelAttribute("course") Course course, RedirectAttributes redirectAttributes) {
        courseService.saveCourse(course);
        redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        return "redirect:/courses";
    }

    // GET /courses/edit/{id} - Show edit form (Maps to edit_course.html)
    @Operation(summary = "Show Edit Course Form", description = "Displays the form to edit an existing course by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        return "edit_course";
    }

    // POST /courses/{id} - Update existing course
    @Operation(summary = "Update Course", description = "Updates an existing course record (POST to ID). Accessible only to ROLE_ADMIN.")
    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute("course") Course courseDetails, RedirectAttributes redirectAttributes) {
        Course existingCourse = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

        existingCourse.setCourseName(courseDetails.getCourseName());
        existingCourse.setDescription(courseDetails.getDescription());

        courseService.saveCourse(existingCourse);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        return "redirect:/courses";
    }

    // GET /courses/delete/{id} - Delete a course
    @Operation(summary = "Delete Course", description = "Deletes a course by ID. Accessible only to ROLE_ADMIN.")
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        return "redirect:/courses";
    }
}