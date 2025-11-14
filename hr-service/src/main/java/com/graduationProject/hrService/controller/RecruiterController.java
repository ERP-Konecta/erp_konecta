package com.graduationProject.hrService.controller;

import com.graduationProject.hrService.dto.ApiResponse;
import com.graduationProject.hrService.dto.RecruiterDTO;
import com.graduationProject.hrService.enums.RecruitmentStatus;
import com.graduationProject.hrService.service.RecruiterService;
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
@RequestMapping("/api/v1/hr/recruiters")
@RequiredArgsConstructor
@Tag(name = "Recruiter Management", description = "APIs for managing recruitment")
@PreAuthorize("hasAuthority('HR')")
public class RecruiterController {
    
    private final RecruiterService recruiterService;
    
    @Operation(summary = "Create a new recruitment record")
    @PostMapping
    public ResponseEntity<ApiResponse<RecruiterDTO>> createRecruiter(@Valid @RequestBody RecruiterDTO dto) {
        RecruiterDTO recruiter = recruiterService.createRecruiter(dto);
        ApiResponse<RecruiterDTO> response = new ApiResponse<>(
            "success",
            "Recruitment record created successfully",
            recruiter
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all recruitment records")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RecruiterDTO>>> getAllRecruiters() {
        List<RecruiterDTO> recruiters = recruiterService.getAllRecruiters();
        ApiResponse<List<RecruiterDTO>> response = new ApiResponse<>(
            "success",
            "Recruitment records retrieved successfully",
            recruiters
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get recruitment record by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecruiterDTO>> getRecruiterById(@PathVariable Long id) {
        RecruiterDTO recruiter = recruiterService.getRecruiterById(id);
        ApiResponse<RecruiterDTO> response = new ApiResponse<>(
            "success",
            "Recruitment record retrieved successfully",
            recruiter
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update recruitment record")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecruiterDTO>> updateRecruiter(
            @PathVariable Long id,
            @Valid @RequestBody RecruiterDTO dto) {
        RecruiterDTO recruiter = recruiterService.updateRecruiter(id, dto);
        ApiResponse<RecruiterDTO> response = new ApiResponse<>(
            "success",
            "Recruitment record updated successfully",
            recruiter
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete recruitment record")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRecruiter(@PathVariable Long id) {
        recruiterService.deleteRecruiter(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Recruitment record deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get recruitment records by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<RecruiterDTO>>> getRecruitersByStatus(
            @PathVariable RecruitmentStatus status) {
        List<RecruiterDTO> recruiters = recruiterService.getRecruitersByStatus(status);
        ApiResponse<List<RecruiterDTO>> response = new ApiResponse<>(
            "success",
            "Recruitment records retrieved successfully",
            recruiters
        );
        return ResponseEntity.ok(response);
    }
}

