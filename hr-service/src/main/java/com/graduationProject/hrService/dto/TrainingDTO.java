package com.graduationProject.hrService.dto;

import com.graduationProject.hrService.enums.TrainingStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO {
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Trainer name is required")
    private String trainerName;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Start date and time is required")
    private LocalDateTime startDateTime;
    
    @NotNull(message = "End date and time is required")
    private LocalDateTime endDateTime;
    
    private TrainingStatus status;
    
    @NotNull(message = "Max participants is required")
    @Min(value = 1, message = "Max participants must be at least 1")
    private Integer maxParticipants;
    
    private Integer currentParticipants;
    
    private String notes;
}

