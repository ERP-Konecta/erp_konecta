package com.graduationProject.financeService.model;

import com.graduationProject.financeService.enums.PayrollStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long employeeId;
    
    @Column(nullable = false)
    private String employeeName;
    
    @Column(nullable = false)
    private String employeeEmail;
    
    @Column(nullable = false)
    private BigDecimal baseSalary;
    
    @Column(nullable = false)
    private BigDecimal allowances;
    
    @Column(nullable = false)
    private BigDecimal deductions;
    
    @Column(nullable = false)
    private BigDecimal netSalary;
    
    @Column(nullable = false)
    private LocalDate payPeriodStart;
    
    @Column(nullable = false)
    private LocalDate payPeriodEnd;
    
    @Column(nullable = false)
    private LocalDate paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollStatus status;
    
    private String notes;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

