package com.example.multitenanthrapi.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data @NoArgsConstructor @AllArgsConstructor
public class Employee {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
