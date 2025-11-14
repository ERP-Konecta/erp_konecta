package com.graduationProject.hrService.service;

import com.graduationProject.hrService.dto.RecruiterDTO;
import com.graduationProject.hrService.enums.RecruitmentStatus;
import com.graduationProject.hrService.exception.ResourceNotFoundException;
import com.graduationProject.hrService.model.Recruiter;
import com.graduationProject.hrService.repository.RecruiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterService {
    
    private final RecruiterRepository recruiterRepository;
    
    @Transactional
    public RecruiterDTO createRecruiter(RecruiterDTO dto) {
        String recruiterName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Recruiter recruiter = Recruiter.builder()
            .candidateName(dto.getCandidateName())
            .candidateEmail(dto.getCandidateEmail())
            .candidatePhone(dto.getCandidatePhone())
            .position(dto.getPosition())
            .department(dto.getDepartment())
            .applicationDate(dto.getApplicationDate())
            .status(dto.getStatus() != null ? dto.getStatus() : RecruitmentStatus.PENDING)
            .expectedSalary(dto.getExpectedSalary())
            .coverLetter(dto.getCoverLetter())
            .interviewNotes(dto.getInterviewNotes())
            .interviewDate(dto.getInterviewDate())
            .recruiterName(recruiterName)
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(recruiterRepository.save(recruiter));
    }
    
    public List<RecruiterDTO> getAllRecruiters() {
        return recruiterRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public RecruiterDTO getRecruiterById(Long id) {
        Recruiter recruiter = recruiterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + id));
        return toDTO(recruiter);
    }
    
    @Transactional
    public RecruiterDTO updateRecruiter(Long id, RecruiterDTO dto) {
        Recruiter recruiter = recruiterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with id: " + id));
        
        recruiter.setCandidateName(dto.getCandidateName());
        recruiter.setCandidateEmail(dto.getCandidateEmail());
        recruiter.setCandidatePhone(dto.getCandidatePhone());
        recruiter.setPosition(dto.getPosition());
        recruiter.setDepartment(dto.getDepartment());
        recruiter.setApplicationDate(dto.getApplicationDate());
        recruiter.setStatus(dto.getStatus());
        recruiter.setExpectedSalary(dto.getExpectedSalary());
        recruiter.setCoverLetter(dto.getCoverLetter());
        recruiter.setInterviewNotes(dto.getInterviewNotes());
        recruiter.setInterviewDate(dto.getInterviewDate());
        recruiter.setUpdatedAt(LocalDate.now());
        
        return toDTO(recruiterRepository.save(recruiter));
    }
    
    @Transactional
    public void deleteRecruiter(Long id) {
        if (!recruiterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recruiter not found with id: " + id);
        }
        recruiterRepository.deleteById(id);
    }
    
    public List<RecruiterDTO> getRecruitersByStatus(RecruitmentStatus status) {
        return recruiterRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private RecruiterDTO toDTO(Recruiter recruiter) {
        return RecruiterDTO.builder()
            .id(recruiter.getId())
            .candidateName(recruiter.getCandidateName())
            .candidateEmail(recruiter.getCandidateEmail())
            .candidatePhone(recruiter.getCandidatePhone())
            .position(recruiter.getPosition())
            .department(recruiter.getDepartment())
            .applicationDate(recruiter.getApplicationDate())
            .status(recruiter.getStatus())
            .expectedSalary(recruiter.getExpectedSalary())
            .coverLetter(recruiter.getCoverLetter())
            .interviewNotes(recruiter.getInterviewNotes())
            .interviewDate(recruiter.getInterviewDate())
            .recruiterName(recruiter.getRecruiterName())
            .build();
    }
}

