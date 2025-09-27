package com.iplus.studentManagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    // Define the name of the security scheme (used below and in annotations)
    private static final String SECURITY_SCHEME_NAME = "BasicAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            // 1. Define General Info (Title, Description)
            .info(new Info()
                .title("Student Management Portal API")
                .version("0.0.1")
                .description("API Documentation for managing Students, Courses, and Enrollments.")
            )
            // 2. Define Security Scheme (Basic Auth for Spring Security)
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic") // Use Basic Auth, which is standard for Spring Security login
                )
            )
            // 3. Apply Security Scheme globally (All endpoints require BasicAuth by default)
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}