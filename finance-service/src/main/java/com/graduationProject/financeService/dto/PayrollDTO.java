package com.graduationProject.financeService.dto;

import com.graduationProject.financeService.enums.PayrollStatus;
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
public class PayrollDTO {
    private Long id;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @NotBlank(message = "Employee name is required")
    private String employeeName;
    
    @NotBlank(message = "Employee email is required")
    @Email(message = "Invalid email format")
    private String employeeEmail;
    
    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base salary must be greater than 0")
    private BigDecimal baseSalary;
    
    @NotNull(message = "Allowances is required")
    @DecimalMin(value = "0.0", message = "Allowances cannot be negative")
    private BigDecimal allowances;
    
    @NotNull(message = "Deductions is required")
    @DecimalMin(value = "0.0", message = "Deductions cannot be negative")
    private BigDecimal deductions;
    
    @NotNull(message = "Pay period start is required")
    private LocalDate payPeriodStart;
    
    @NotNull(message = "Pay period end is required")
    private LocalDate payPeriodEnd;
    
    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;
    
    private PayrollStatus status;
    private String notes;
}

