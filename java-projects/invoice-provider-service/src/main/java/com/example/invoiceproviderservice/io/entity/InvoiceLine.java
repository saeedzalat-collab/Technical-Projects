package com.example.invoiceproviderservice.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoicelines")
@Data @NoArgsConstructor @AllArgsConstructor
public class InvoiceLine {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
