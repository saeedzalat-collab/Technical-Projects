package com.example.multitenanthrapi.ui.model;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RequestModel {
    private String id;
    private String name;
    private String description;
    private String email;
    private boolean active;
    private LocalDate date;
    private Double amount;
    private String status;
}

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseModel {
    private String id;
    private String message;
    private Object data;
}
