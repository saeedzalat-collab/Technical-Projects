package com.example.leavemanagementservice.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.leavemanagementservice.io.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {
}
