package com.graduationProject.hrService.dto;

import com.graduationProject.hrService.enums.RecruitmentStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterDTO {
    private Long id;
    
    @NotBlank(message = "Candidate name is required")
    private String candidateName;
    
    @NotBlank(message = "Candidate email is required")
    @Email(message = "Invalid email format")
    private String candidateEmail;
    
    @NotBlank(message = "Candidate phone is required")
    private String candidatePhone;
    
    @NotBlank(message = "Position is required")
    private String position;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;
    
    private RecruitmentStatus status;
    
    @DecimalMin(value = "0.0", message = "Expected salary cannot be negative")
    private BigDecimal expectedSalary;
    
    private String coverLetter;
    
    private String interviewNotes;
    
    private LocalDate interviewDate;
    
    private String recruiterName;
}

