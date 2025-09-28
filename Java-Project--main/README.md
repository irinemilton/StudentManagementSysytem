🎓 Student Management Portal

The Student Management Portal (SMP) is a secure and efficient web application designed to simplify the management of students, courses, and enrollments. Built with Spring Boot, MySQL, and Thymeleaf, it provides CRUD functionality, role-based access control, and API documentation out-of-the-box.

✨ Features at a Glance

🧑‍🎓 Student Records – Add, update, view, and delete student details.

📘 Course Management – Manage course offerings seamlessly.

🔗 Enrollments – Track student-course enrollment data.

🔒 Role-Based Security – Admins manage data, users can only view.

📑 API Docs – Integrated Swagger UI for REST API exploration.

🛠️ Tech Stack
Layer	Technology
Language	Java 17+
Framework	Spring Boot 3.5.6
Web Layer	Spring MVC + Thymeleaf
Database	MySQL (via Spring Data JPA)
Security	Spring Security
Documentation	SpringDoc OpenAPI / Swagger
⚙️ Setup Guide
1. Requirements

Before running SMP, ensure you have:

JDK 17 or newer

Maven (or Maven Wrapper included in project)

MySQL running locally on port 3306

2. Database Settings

Configure your database in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/fullstack_project
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update


The application will auto-create the database and tables (students, courses, enrollments, users) if they don’t exist.

▶️ Getting Started
Clone the Repo
git clone https://github.com/KailasVS666/Java-Project-.git
cd smp-skeleton-main

Build & Run
./mvnw clean install
./mvnw spring-boot:run


Application will be available at:
👉 http://localhost:8080

🔑 Authentication Flow

Sign Up at /signup to create your first user.

Assign yourself the ROLE_ADMIN role to unlock full functionality.

Log in at /login to access the portal.

Role Permissions
Action	Admin (ROLE_ADMIN)	User (ROLE_USER)
View lists (students, courses, enrollments)	✅	✅
Create/Edit/Update records	✅	❌
Delete records	✅	❌
📖 API Documentation

For API exploration and testing:

Log in as Admin.

Go to 👉 http://localhost:8080/swagger-ui.html

Endpoints are categorized (Students, Courses, Enrollments, etc.), with role requirements clearly documented.

✅ Summary

The Student Management Portal provides a full-stack solution for educational institutions to manage students and courses securely. With role-based security, dynamic templates, and robust API documentation, it’s ready for real-world usage and extension.
