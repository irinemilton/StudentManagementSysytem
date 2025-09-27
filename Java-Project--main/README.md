

# 📚 Student Management Portal (SMP)

A secure, web-based Student Management Portal application built using **Spring Boot**, **MySQL**, and **Thymeleaf**. This application provides CRUD operations for students, courses, and enrollment management, enforced by role-based security.

## 🚀 Project Overview

The Student Management Portal allows administrators to manage student records, course offerings, and enrollment status, while regular users can view the data. The application uses JPA for database persistence and Spring Security for authentication and authorization.

## ⚙️ Technologies Used

| Category | Component | Description |
| :--- | :--- | :--- |
| **Backend** | **Java 17+** | Primary language for business logic. |
| **Framework** | **Spring Boot 3.5.6** | Simplifies setup and configuration of the application. |
| **Web** | **Spring Web (MVC)** | Handles HTTP requests and integrates the view layer. |
| **View Layer** | **Thymeleaf** | Server-side template engine for dynamic HTML rendering. |
| **Persistence**| **Spring Data JPA**| Maps Java entities to database tables (ORM). |
| **Database** | **MySQL** | Relational database for storing data. |
| **Security** | **Spring Security** | Handles authentication (`/login`, `/signup`) and role-based authorization. |
| **Documentation** | **SpringDoc OpenAPI**| Automatic API documentation via Swagger UI. |

## 📦 Setup and Installation

### Prerequisites

  * Java Development Kit (JDK) 17 or higher
  * Apache Maven
  * MySQL Server (running locally on port 3306)

### Database Configuration

The application is configured to connect to MySQL using the following properties:

| Property | Value |
| :--- | :--- |
| **Database Name** | `fullstack_project` |
| **URL** | `jdbc:mysql://localhost:3306/fullstack_project` |
| **Username** | `root` |
| **Password** | `root` |

The `spring.jpa.hibernate.ddl-auto=update` setting will automatically create the `fullstack_project` database (if it doesn't exist) and generate all necessary tables (`students`, `courses`, `enrollments`, `users`) upon first run.

## ▶️ Running the Application

1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/KailasVS666/Java-Project-.git

    cd smp-skeleton-main
    ```

2.  **Build the Project:**

    ```bash
    # Use the Maven Wrapper script to clean and build the project
    ./mvnw clean install
    ```

3.  **Run the Application:**

    ```bash
    ./mvnw spring-boot:run
    ```

    The application will start on port `8080`.

4.  **Access the Portal:**
    Open your browser and navigate to: **`http://localhost:8080`**

## 🔑 Authentication and Authorization

All management pages are secured by Spring Security.

### Initial Access

1.  **Register:** Navigate to the Sign Up page: `http://localhost:8080/signup`
2.  **Create an Admin:** Register your first user with the **`ROLE_ADMIN`** role to gain full access to the portal.
3.  **Log In:** Use your new credentials on the Login page.

### Role-Based Access Control (RBAC)

The application implements granular control based on roles:

| Path/Operation | `ROLE_ADMIN` | `ROLE_USER` |
| :--- | :--- | :--- |
| **View Lists** (`/students`, `/courses`, `/enrollments`) | ✅ Full Access | ✅ View Only |
| **Create/Edit/Update** (`/new`, `/edit`, `POST` methods) | ✅ Allowed | ❌ Denied |
| **Delete** (`/delete/{id}`) | ✅ Allowed | ❌ Denied |

## 📊 API Documentation (Swagger UI)

The API endpoints are automatically documented using SpringDoc OpenAPI.

1.  **Log in** as an Admin user.
2.  Access the interactive documentation at:
    **`http://localhost:8080/swagger-ui.html`**

The endpoints are organized by tags (**Students**, **Courses**, **Enrollments**, etc.), and include descriptions of required roles for each operation.
