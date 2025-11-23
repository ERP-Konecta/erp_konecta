package com.graduationProject.hrService.model;

import com.graduationProject.hrService.enums.PerformanceRating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "performances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
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
    private String reviewPeriod;
    
    @Column(nullable = false)
    private LocalDate reviewDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerformanceRating overallRating;
    
    @Column(nullable = false, length = 2000)
    private String strengths;
    
    @Column(nullable = false, length = 2000)
    private String areasForImprovement;
    
    @Column(length = 2000)
    private String goals;
    
    @Column(nullable = false)
    private String reviewedBy;
    
    private String reviewerNotes;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

