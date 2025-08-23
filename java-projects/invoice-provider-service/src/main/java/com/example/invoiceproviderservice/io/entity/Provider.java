package com.example.invoiceproviderservice.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "providers")
@Data @NoArgsConstructor @AllArgsConstructor
public class Provider {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
