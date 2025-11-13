package com.graduationProject.gpManagementSystem.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduationProject.gpManagementSystem.dto.ApiResponse;
import com.graduationProject.gpManagementSystem.dto.ChangePasswordDTO;
import com.graduationProject.gpManagementSystem.dto.CreateUserDTO;
import com.graduationProject.gpManagementSystem.dto.LoginRequestDTO;
import com.graduationProject.gpManagementSystem.dto.LoginResponseDTO;
import com.graduationProject.gpManagementSystem.model.User;
import com.graduationProject.gpManagementSystem.security.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;



    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(
            @Valid @RequestBody CreateUserDTO request
    ) {
        User user = service.registerUser(request);
        ApiResponse<User> response = new ApiResponse<>(
            "success",
            "User registered successfully",
            user
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }








    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(service.login(request));
    }

    @PutMapping("/{userId}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> approveUserRegistration(@PathVariable int userId) {
        service.approveUserRegistration(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/reject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> rejectUserRegistration(@PathVariable int userId) {
        service.rejectUserRegistration(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable int userId,
            @RequestBody ChangePasswordDTO request) {
        service.changePassword(userId, request);
        ApiResponse<Void> response = new ApiResponse<>(
                "success",
                "Password changed successfully"
        );
        return ResponseEntity.ok(response);
    }
}
