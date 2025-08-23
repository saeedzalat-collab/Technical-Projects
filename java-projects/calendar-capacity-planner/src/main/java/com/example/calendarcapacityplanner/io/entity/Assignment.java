package com.example.calendarcapacityplanner.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assignments")
@Data @NoArgsConstructor @AllArgsConstructor
public class Assignment {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
