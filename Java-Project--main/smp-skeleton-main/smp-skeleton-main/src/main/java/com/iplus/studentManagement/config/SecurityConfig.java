package com.iplus.studentManagement.config;

import com.iplus.studentManagement.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // --- Authentication & User Details (No change needed here) ---

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            com.iplus.studentManagement.entity.User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Could not find user " + username);
            }
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRole())
            );
        };
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    // --- Authorization & Request Configuration (Updated logic) ---

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. PUBLIC ACCESS
                .requestMatchers("/", "/login", "/signup", "/register", "/css/**", "/js/**", "/webjars/**").permitAll()
                
                // 2. ADMIN ONLY: All Delete operations (most restrictive)
                .requestMatchers("/students/delete/**", "/courses/delete/**", "/enrollments/delete/**").hasRole("ADMIN")
                
                // 3. ADMIN ONLY: All Create/Edit/Update forms and POST actions
                .requestMatchers("/students/new", "/students/edit/**", 
                                 "/courses/new", "/courses/edit/**", 
                                 "/enrollments/new", "/enrollments/edit/**", "/enrollments/update/**")
                    .hasRole("ADMIN") 
                
                // 4. ADMIN ONLY: All POST requests to the main paths (Create/Update logic)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/students", "/courses", "/enrollments").hasRole("ADMIN")

                // 5. USER/ADMIN ACCESS: All remaining authenticated requests (viewing lists, grades, details)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") 
                .permitAll()
            );
        return http.build();
    }
}