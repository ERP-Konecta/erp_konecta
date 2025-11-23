package com.graduationProject.financeService.repository;

import com.graduationProject.financeService.model.Payroll;
import com.graduationProject.financeService.enums.PayrollStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    List<Payroll> findByStatus(PayrollStatus status);
    List<Payroll> findByPaymentDateBetween(LocalDate start, LocalDate end);
}

