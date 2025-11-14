package com.graduationProject.hrService.controller;

import com.graduationProject.hrService.dto.ApiResponse;
import com.graduationProject.hrService.dto.TrainingDTO;
import com.graduationProject.hrService.enums.TrainingStatus;
import com.graduationProject.hrService.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr/trainings")
@RequiredArgsConstructor
@Tag(name = "Training Management", description = "APIs for managing trainings")
@PreAuthorize("hasAuthority('HR')")
public class TrainingController {
    
    private final TrainingService trainingService;
    
    @Operation(summary = "Create a new training")
    @PostMapping
    public ResponseEntity<ApiResponse<TrainingDTO>> createTraining(@Valid @RequestBody TrainingDTO dto) {
        TrainingDTO training = trainingService.createTraining(dto);
        ApiResponse<TrainingDTO> response = new ApiResponse<>(
            "success",
            "Training created successfully",
            training
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all trainings")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TrainingDTO>>> getAllTrainings() {
        List<TrainingDTO> trainings = trainingService.getAllTrainings();
        ApiResponse<List<TrainingDTO>> response = new ApiResponse<>(
            "success",
            "Trainings retrieved successfully",
            trainings
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get training by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingDTO>> getTrainingById(@PathVariable Long id) {
        TrainingDTO training = trainingService.getTrainingById(id);
        ApiResponse<TrainingDTO> response = new ApiResponse<>(
            "success",
            "Training retrieved successfully",
            training
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update training")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingDTO>> updateTraining(
            @PathVariable Long id,
            @Valid @RequestBody TrainingDTO dto) {
        TrainingDTO training = trainingService.updateTraining(id, dto);
        ApiResponse<TrainingDTO> response = new ApiResponse<>(
            "success",
            "Training updated successfully",
            training
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete training")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Training deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get trainings by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TrainingDTO>>> getTrainingsByStatus(
            @PathVariable TrainingStatus status) {
        List<TrainingDTO> trainings = trainingService.getTrainingsByStatus(status);
        ApiResponse<List<TrainingDTO>> response = new ApiResponse<>(
            "success",
            "Trainings retrieved successfully",
            trainings
        );
        return ResponseEntity.ok(response);
    }
}

