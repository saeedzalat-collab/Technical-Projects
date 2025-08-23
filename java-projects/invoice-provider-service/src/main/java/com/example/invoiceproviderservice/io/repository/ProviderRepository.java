package com.example.invoiceproviderservice.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.invoiceproviderservice.io.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
}
