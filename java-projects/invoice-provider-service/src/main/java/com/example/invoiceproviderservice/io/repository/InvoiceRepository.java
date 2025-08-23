package com.example.invoiceproviderservice.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.invoiceproviderservice.io.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
