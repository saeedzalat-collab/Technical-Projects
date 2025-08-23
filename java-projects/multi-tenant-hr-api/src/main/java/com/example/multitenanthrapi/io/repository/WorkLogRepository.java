package com.example.multitenanthrapi.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.multitenanthrapi.io.entity.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, String> {
}
