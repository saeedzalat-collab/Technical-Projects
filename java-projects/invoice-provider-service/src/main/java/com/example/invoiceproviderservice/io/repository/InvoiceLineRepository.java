package com.example.invoiceproviderservice.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.invoiceproviderservice.io.entity.InvoiceLine;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, String> {
}
