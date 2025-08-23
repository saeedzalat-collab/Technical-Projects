package com.example.leavemanagementservice.service.impl;

import com.example.leavemanagementservice.ui.model.RequestModel;
import com.example.leavemanagementservice.ui.model.ResponseModel;
import com.example.leavemanagementservice.service.LeaveRequestService;
import com.example.leavemanagementservice.io.entity.LeaveRequest;
import com.example.leavemanagementservice.io.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private LeaveRequestRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        LeaveRequest e = new LeaveRequest();
        e.setId(UUID.randomUUID().toString());
        e.setName(req.getName());
        e.setDescription(req.getDescription());
        e.setActive(req.isActive());
        repo.save(e);
        return new ResponseModel(e.getId(), "Created", e);
    }

    @Override
    public ResponseModel get(String id) {
        return repo.findById(id)
            .map(e -> new ResponseModel(e.getId(), "OK", e))
            .orElse(new ResponseModel(null, "Not Found", null));
    }

    @Override
    public ResponseModel list() {
        return new ResponseModel(null, "OK", repo.findAll());
    }
}
