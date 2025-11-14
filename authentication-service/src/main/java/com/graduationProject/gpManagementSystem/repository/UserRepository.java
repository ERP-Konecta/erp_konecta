package com.graduationProject.gpManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graduationProject.gpManagementSystem.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByStatus(String status);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long senderId);
    // Optional<User> findByUserId(int userId);

    

}