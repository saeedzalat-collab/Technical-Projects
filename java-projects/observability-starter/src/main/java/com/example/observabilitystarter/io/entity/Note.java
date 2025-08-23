package com.example.observabilitystarter.io.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notes")
@Data @NoArgsConstructor @AllArgsConstructor
public class Note {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean active;
}
