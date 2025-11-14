package com.graduationProject.hrService.model;

import com.graduationProject.hrService.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recruiters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String candidateName;
    
    @Column(nullable = false)
    private String candidateEmail;
    
    @Column(nullable = false)
    private String candidatePhone;
    
    @Column(nullable = false)
    private String position;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private LocalDate applicationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus status;
    
    private BigDecimal expectedSalary;
    
    private String resumePath;
    
    private String coverLetter;
    
    private String interviewNotes;
    
    private LocalDate interviewDate;
    
    private String recruiterName;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

