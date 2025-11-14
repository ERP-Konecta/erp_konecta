package com.graduationProject.hrService.model;

import com.graduationProject.hrService.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
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
    private LocalDate attendanceDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;
    
    private LocalTime checkInTime;
    
    private LocalTime checkOutTime;
    
    private Integer workingHours;
    
    private Integer overtimeHours;
    
    private String notes;
    
    @Column(nullable = false)
    private String recordedBy;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

