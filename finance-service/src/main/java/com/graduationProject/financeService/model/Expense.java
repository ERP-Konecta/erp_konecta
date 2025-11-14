package com.graduationProject.financeService.model;

import com.graduationProject.financeService.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseCategory category;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDate expenseDate;
    
    private String description;
    
    private String receiptPath;
    
    @Column(nullable = false)
    private String createdBy;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

