package com.graduationProject.hrService.repository;

import com.graduationProject.hrService.model.Recruiter;
import com.graduationProject.hrService.enums.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    List<Recruiter> findByStatus(RecruitmentStatus status);
    List<Recruiter> findByPosition(String position);
    List<Recruiter> findByDepartment(String department);
    List<Recruiter> findByCandidateEmail(String email);
}

