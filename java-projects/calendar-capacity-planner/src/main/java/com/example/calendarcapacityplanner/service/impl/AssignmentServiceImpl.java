package com.example.calendarcapacityplanner.service.impl;

import com.example.calendarcapacityplanner.ui.model.RequestModel;
import com.example.calendarcapacityplanner.ui.model.ResponseModel;
import com.example.calendarcapacityplanner.service.AssignmentService;
import com.example.calendarcapacityplanner.io.entity.Assignment;
import com.example.calendarcapacityplanner.io.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        Assignment e = new Assignment();
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
