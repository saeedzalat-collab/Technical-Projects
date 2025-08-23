package com.example.calendarcapacityplanner.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.calendarcapacityplanner.io.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
}
