package com.graduationProject.hrService.dto;

import com.graduationProject.hrService.enums.AttendanceStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @NotBlank(message = "Employee name is required")
    private String employeeName;
    
    @NotBlank(message = "Employee email is required")
    @Email(message = "Invalid email format")
    private String employeeEmail;
    
    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;
    
    @NotNull(message = "Status is required")
    private AttendanceStatus status;
    
    private LocalTime checkInTime;
    
    private LocalTime checkOutTime;
    
    @Min(value = 0, message = "Working hours cannot be negative")
    private Integer workingHours;
    
    @Min(value = 0, message = "Overtime hours cannot be negative")
    private Integer overtimeHours;
    
    private String notes;
}

