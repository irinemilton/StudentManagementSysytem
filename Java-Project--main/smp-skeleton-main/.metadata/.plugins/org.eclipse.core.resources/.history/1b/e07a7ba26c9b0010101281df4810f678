package com.iplus.studentManagement.repository;

import com.iplus.studentManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method used by Spring Security to find a user by username
    User findByUsername(String username);
}