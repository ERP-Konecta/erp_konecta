package com.graduationProject.gpManagementSystem.security;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.graduationProject.gpManagementSystem.dto.*;
import com.graduationProject.gpManagementSystem.enums.Status;
import com.graduationProject.gpManagementSystem.exception.InvalidPasswordException;
import com.graduationProject.gpManagementSystem.exception.ResourceNotFoundException;
import com.graduationProject.gpManagementSystem.exception.UserAlreadyExistException;
import com.graduationProject.gpManagementSystem.model.User;
import com.graduationProject.gpManagementSystem.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder; 
    private final UserRepository repository;
    private final JwtUtils jwtService;
    private final AuthenticationManager authenticationManager;

    public User registerUser(CreateUserDTO request) {
        // Validate email
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("A user with this email already exists.");
        }

        // Validate password
        if (!isValidPassword(request.getPassword())) {
            throw new InvalidPasswordException("Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.");
        }

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .status(Status.ACCEPTED)
            .build();
        
        return repository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        ));
        
        User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            
        if (user.getStatus().toString().equals("ACCEPTED")) {
            String jwtToken = jwtService.generateToken(user);
            return LoginResponseDTO.builder()
                .token(jwtToken)
                .statusCode(HttpStatus.ACCEPTED)
                .tokenType("Bearer")
                .message("login successful")
                .build();
        } else {
            return LoginResponseDTO.builder()
                .statusCode(HttpStatus.FORBIDDEN)
                .message("Sorry, you can't login until admin accepts your registration")
                .build();
        }
    }

    public void approveUserRegistration(int userId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(Status.ACCEPTED);
            repository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    public void rejectUserRegistration(int userId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(Status.REJECTED);
            repository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    public void changePassword(Integer userId, ChangePasswordDTO request) {
        User user = repository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect.");
        }

        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new InvalidPasswordException("New password must be different from the old password.");
        }

        if (!isValidPassword(request.getNewPassword())) {
            throw new InvalidPasswordException("New password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }

    // Utility function
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
}
