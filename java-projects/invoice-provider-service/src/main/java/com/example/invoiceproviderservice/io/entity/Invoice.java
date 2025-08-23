package com.example.invoiceproviderservice.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoices")
@Data @NoArgsConstructor @AllArgsConstructor
public class Invoice {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
