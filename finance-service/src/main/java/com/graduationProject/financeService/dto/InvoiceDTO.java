package com.graduationProject.financeService.dto;

import com.graduationProject.financeService.enums.InvoiceStatus;
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
public class InvoiceDTO {
    private Long id;
    
    @NotBlank(message = "Invoice number is required")
    private String invoiceNumber;
    
    @NotBlank(message = "Client name is required")
    private String clientName;
    
    @NotBlank(message = "Client email is required")
    @Email(message = "Invalid email format")
    private String clientEmail;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @NotNull(message = "Tax amount is required")
    @DecimalMin(value = "0.0", message = "Tax amount cannot be negative")
    private BigDecimal taxAmount;
    
    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    private InvoiceStatus status;
    private String description;
}

