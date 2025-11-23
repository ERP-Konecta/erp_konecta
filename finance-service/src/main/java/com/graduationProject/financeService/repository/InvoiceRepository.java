package com.graduationProject.financeService.repository;

import com.graduationProject.financeService.model.Invoice;
import com.graduationProject.financeService.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByClientEmail(String clientEmail);
    List<Invoice> findByDueDateBeforeAndStatus(LocalDate date, InvoiceStatus status);
}

