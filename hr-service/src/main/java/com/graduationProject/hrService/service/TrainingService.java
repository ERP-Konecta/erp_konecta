package com.graduationProject.hrService.service;

import com.graduationProject.hrService.dto.TrainingDTO;
import com.graduationProject.hrService.enums.TrainingStatus;
import com.graduationProject.hrService.exception.ResourceNotFoundException;
import com.graduationProject.hrService.model.Training;
import com.graduationProject.hrService.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {
    
    private final TrainingRepository trainingRepository;
    
    @Transactional
    public TrainingDTO createTraining(TrainingDTO dto) {
        String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Training training = Training.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .trainerName(dto.getTrainerName())
            .location(dto.getLocation())
            .startDateTime(dto.getStartDateTime())
            .endDateTime(dto.getEndDateTime())
            .status(dto.getStatus() != null ? dto.getStatus() : TrainingStatus.SCHEDULED)
            .maxParticipants(dto.getMaxParticipants())
            .currentParticipants(0)
            .notes(dto.getNotes())
            .createdBy(createdBy)
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(trainingRepository.save(training));
    }
    
    public List<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public TrainingDTO getTrainingById(Long id) {
        Training training = trainingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + id));
        return toDTO(training);
    }
    
    @Transactional
    public TrainingDTO updateTraining(Long id, TrainingDTO dto) {
        Training training = trainingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + id));
        
        training.setTitle(dto.getTitle());
        training.setDescription(dto.getDescription());
        training.setTrainerName(dto.getTrainerName());
        training.setLocation(dto.getLocation());
        training.setStartDateTime(dto.getStartDateTime());
        training.setEndDateTime(dto.getEndDateTime());
        training.setStatus(dto.getStatus());
        training.setMaxParticipants(dto.getMaxParticipants());
        training.setCurrentParticipants(dto.getCurrentParticipants());
        training.setNotes(dto.getNotes());
        training.setUpdatedAt(LocalDate.now());
        
        return toDTO(trainingRepository.save(training));
    }
    
    @Transactional
    public void deleteTraining(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Training not found with id: " + id);
        }
        trainingRepository.deleteById(id);
    }
    
    public List<TrainingDTO> getTrainingsByStatus(TrainingStatus status) {
        return trainingRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private TrainingDTO toDTO(Training training) {
        return TrainingDTO.builder()
            .id(training.getId())
            .title(training.getTitle())
            .description(training.getDescription())
            .trainerName(training.getTrainerName())
            .location(training.getLocation())
            .startDateTime(training.getStartDateTime())
            .endDateTime(training.getEndDateTime())
            .status(training.getStatus())
            .maxParticipants(training.getMaxParticipants())
            .currentParticipants(training.getCurrentParticipants())
            .notes(training.getNotes())
            .build();
    }
}

