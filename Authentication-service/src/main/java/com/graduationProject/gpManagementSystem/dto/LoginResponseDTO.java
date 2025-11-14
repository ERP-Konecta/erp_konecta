package com.graduationProject.gpManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    
    private String token;
    private String tokenType;
    private String message;
    private HttpStatus statusCode;
}
