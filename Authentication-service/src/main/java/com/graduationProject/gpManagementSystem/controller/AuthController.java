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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and user management APIs")
public class AuthController {

        private final AuthService service;

        @Operation(summary = "Register a new user", description = "Register a new user in the system. User will be pending approval by admin.")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered successfully"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("/register")
        public ResponseEntity<ApiResponse<User>> register(
                        @Valid @RequestBody CreateUserDTO request) {
                User user = service.registerUser(request);
                ApiResponse<User> response = new ApiResponse<>(
                                "success",
                                "User registered successfully",
                                user);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "User login", description = "Authenticate user and receive JWT token")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials")
        })
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(
                        @RequestBody LoginRequestDTO request) {
                return ResponseEntity.ok(service.login(request));
        }

        @Operation(summary = "Approve user registration", description = "Approve a pending user registration. Admin only.")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User approved successfully"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
        })
        @PutMapping("/{userId}/approve")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Void> approveUserRegistration(@PathVariable int userId) {
                service.approveUserRegistration(userId);
                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Reject user registration", description = "Reject a pending user registration. Admin only.")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User rejected successfully"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
        })
        @PutMapping("/{userId}/reject")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Void> rejectUserRegistration(@PathVariable int userId) {
                service.rejectUserRegistration(userId);
                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Change user password", description = "Change password for a user. Requires authentication.")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password changed successfully"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid password data"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
        })
        @PutMapping("/{userId}/change-password")
        public ResponseEntity<ApiResponse<Void>> changePassword(
                        @PathVariable int userId,
                        @RequestBody ChangePasswordDTO request) {
                service.changePassword(userId, request);
                ApiResponse<Void> response = new ApiResponse<>(
                                "success",
                                "Password changed successfully");
                return ResponseEntity.ok(response);
        }
}
