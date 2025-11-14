package com.graduationProject.hrService.model;

import com.graduationProject.hrService.enums.TrainingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String trainerName;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingStatus status;
    
    @Column(nullable = false)
    private Integer maxParticipants;
    
    private Integer currentParticipants;
    
    private String materialsPath;
    
    private String notes;
    
    @Column(nullable = false)
    private String createdBy;
    
    @Column(nullable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
}

