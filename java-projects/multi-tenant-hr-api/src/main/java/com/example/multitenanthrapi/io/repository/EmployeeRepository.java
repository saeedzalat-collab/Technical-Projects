package com.example.multitenanthrapi.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.multitenanthrapi.io.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
