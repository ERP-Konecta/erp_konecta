package com.graduationProject.financeService.repository;

import com.graduationProject.financeService.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByCategory(String category);
    List<Budget> findByStartDateBetween(LocalDate start, LocalDate end);
}

