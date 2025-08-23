package com.example.calendarcapacityplanner.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.calendarcapacityplanner.io.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
