package com.graduationProject.hrService.controller;

import com.graduationProject.hrService.dto.ApiResponse;
import com.graduationProject.hrService.dto.PerformanceDTO;
import com.graduationProject.hrService.enums.PerformanceRating;
import com.graduationProject.hrService.service.PerformanceService;
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
@RequestMapping("/api/v1/hr/performances")
@RequiredArgsConstructor
@Tag(name = "Performance Management", description = "APIs for managing performance reviews")
@PreAuthorize("hasAuthority('HR')")
public class PerformanceController {
    
    private final PerformanceService performanceService;
    
    @Operation(summary = "Create a new performance review")
    @PostMapping
    public ResponseEntity<ApiResponse<PerformanceDTO>> createPerformance(@Valid @RequestBody PerformanceDTO dto) {
        PerformanceDTO performance = performanceService.createPerformance(dto);
        ApiResponse<PerformanceDTO> response = new ApiResponse<>(
            "success",
            "Performance review created successfully",
            performance
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all performance reviews")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PerformanceDTO>>> getAllPerformances() {
        List<PerformanceDTO> performances = performanceService.getAllPerformances();
        ApiResponse<List<PerformanceDTO>> response = new ApiResponse<>(
            "success",
            "Performance reviews retrieved successfully",
            performances
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get performance review by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PerformanceDTO>> getPerformanceById(@PathVariable Long id) {
        PerformanceDTO performance = performanceService.getPerformanceById(id);
        ApiResponse<PerformanceDTO> response = new ApiResponse<>(
            "success",
            "Performance review retrieved successfully",
            performance
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update performance review")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PerformanceDTO>> updatePerformance(
            @PathVariable Long id,
            @Valid @RequestBody PerformanceDTO dto) {
        PerformanceDTO performance = performanceService.updatePerformance(id, dto);
        ApiResponse<PerformanceDTO> response = new ApiResponse<>(
            "success",
            "Performance review updated successfully",
            performance
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete performance review")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Performance review deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get performance reviews by employee ID")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<PerformanceDTO>>> getPerformancesByEmployeeId(
            @PathVariable Long employeeId) {
        List<PerformanceDTO> performances = performanceService.getPerformancesByEmployeeId(employeeId);
        ApiResponse<List<PerformanceDTO>> response = new ApiResponse<>(
            "success",
            "Performance reviews retrieved successfully",
            performances
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get performance reviews by rating")
    @GetMapping("/rating/{rating}")
    public ResponseEntity<ApiResponse<List<PerformanceDTO>>> getPerformancesByRating(
            @PathVariable PerformanceRating rating) {
        List<PerformanceDTO> performances = performanceService.getPerformancesByRating(rating);
        ApiResponse<List<PerformanceDTO>> response = new ApiResponse<>(
            "success",
            "Performance reviews retrieved successfully",
            performances
        );
        return ResponseEntity.ok(response);
    }
}

