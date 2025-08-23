package com.example.multitenanthrapi.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.multitenanthrapi.io.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, String> {
}
