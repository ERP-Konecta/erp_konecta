package com.graduationProject.hrService.service;

import com.graduationProject.hrService.dto.PerformanceDTO;
import com.graduationProject.hrService.enums.PerformanceRating;
import com.graduationProject.hrService.exception.ResourceNotFoundException;
import com.graduationProject.hrService.model.Performance;
import com.graduationProject.hrService.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    
    private final PerformanceRepository performanceRepository;
    
    @Transactional
    public PerformanceDTO createPerformance(PerformanceDTO dto) {
        String reviewedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Performance performance = Performance.builder()
            .employeeId(dto.getEmployeeId())
            .employeeName(dto.getEmployeeName())
            .employeeEmail(dto.getEmployeeEmail())
            .reviewPeriod(dto.getReviewPeriod())
            .reviewDate(dto.getReviewDate())
            .overallRating(dto.getOverallRating())
            .strengths(dto.getStrengths())
            .areasForImprovement(dto.getAreasForImprovement())
            .goals(dto.getGoals())
            .reviewedBy(reviewedBy)
            .reviewerNotes(dto.getReviewerNotes())
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(performanceRepository.save(performance));
    }
    
    public List<PerformanceDTO> getAllPerformances() {
        return performanceRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public PerformanceDTO getPerformanceById(Long id) {
        Performance performance = performanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Performance not found with id: " + id));
        return toDTO(performance);
    }
    
    @Transactional
    public PerformanceDTO updatePerformance(Long id, PerformanceDTO dto) {
        Performance performance = performanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Performance not found with id: " + id));
        
        performance.setEmployeeName(dto.getEmployeeName());
        performance.setEmployeeEmail(dto.getEmployeeEmail());
        performance.setReviewPeriod(dto.getReviewPeriod());
        performance.setReviewDate(dto.getReviewDate());
        performance.setOverallRating(dto.getOverallRating());
        performance.setStrengths(dto.getStrengths());
        performance.setAreasForImprovement(dto.getAreasForImprovement());
        performance.setGoals(dto.getGoals());
        performance.setReviewedBy(dto.getReviewedBy());
        performance.setReviewerNotes(dto.getReviewerNotes());
        performance.setUpdatedAt(LocalDate.now());
        
        return toDTO(performanceRepository.save(performance));
    }
    
    @Transactional
    public void deletePerformance(Long id) {
        if (!performanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Performance not found with id: " + id);
        }
        performanceRepository.deleteById(id);
    }
    
    public List<PerformanceDTO> getPerformancesByEmployeeId(Long employeeId) {
        return performanceRepository.findByEmployeeId(employeeId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<PerformanceDTO> getPerformancesByRating(PerformanceRating rating) {
        return performanceRepository.findByOverallRating(rating).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private PerformanceDTO toDTO(Performance performance) {
        return PerformanceDTO.builder()
            .id(performance.getId())
            .employeeId(performance.getEmployeeId())
            .employeeName(performance.getEmployeeName())
            .employeeEmail(performance.getEmployeeEmail())
            .reviewPeriod(performance.getReviewPeriod())
            .reviewDate(performance.getReviewDate())
            .overallRating(performance.getOverallRating())
            .strengths(performance.getStrengths())
            .areasForImprovement(performance.getAreasForImprovement())
            .goals(performance.getGoals())
            .reviewedBy(performance.getReviewedBy())
            .reviewerNotes(performance.getReviewerNotes())
            .build();
    }
}

