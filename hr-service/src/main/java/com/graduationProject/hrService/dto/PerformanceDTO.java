package com.graduationProject.hrService.dto;

import com.graduationProject.hrService.enums.PerformanceRating;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @NotBlank(message = "Employee name is required")
    private String employeeName;
    
    @NotBlank(message = "Employee email is required")
    @Email(message = "Invalid email format")
    private String employeeEmail;
    
    @NotBlank(message = "Review period is required")
    private String reviewPeriod;
    
    @NotNull(message = "Review date is required")
    private LocalDate reviewDate;
    
    @NotNull(message = "Overall rating is required")
    private PerformanceRating overallRating;
    
    @NotBlank(message = "Strengths are required")
    @Size(max = 2000, message = "Strengths cannot exceed 2000 characters")
    private String strengths;
    
    @NotBlank(message = "Areas for improvement are required")
    @Size(max = 2000, message = "Areas for improvement cannot exceed 2000 characters")
    private String areasForImprovement;
    
    @Size(max = 2000, message = "Goals cannot exceed 2000 characters")
    private String goals;
    
    @NotBlank(message = "Reviewed by is required")
    private String reviewedBy;
    
    @Size(max = 2000, message = "Reviewer notes cannot exceed 2000 characters")
    private String reviewerNotes;
}

