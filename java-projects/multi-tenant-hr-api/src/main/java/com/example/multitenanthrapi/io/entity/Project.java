package com.example.multitenanthrapi.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projects")
@Data @NoArgsConstructor @AllArgsConstructor
public class Project {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
