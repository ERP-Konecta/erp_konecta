package com.graduationProject.financeService.dto;

import com.graduationProject.financeService.enums.BudgetPeriod;
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
public class BudgetDTO {
    private Long id;
    
    @NotBlank(message = "Budget name is required")
    private String name;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Allocated amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Allocated amount must be greater than 0")
    private BigDecimal allocatedAmount;
    
    private BigDecimal spentAmount;
    
    @NotNull(message = "Period is required")
    private BudgetPeriod period;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    private String description;
}

