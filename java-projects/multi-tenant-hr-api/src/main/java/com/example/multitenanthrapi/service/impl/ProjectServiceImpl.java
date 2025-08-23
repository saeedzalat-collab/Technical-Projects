package com.example.multitenanthrapi.service.impl;

import com.example.multitenanthrapi.ui.model.RequestModel;
import com.example.multitenanthrapi.ui.model.ResponseModel;
import com.example.multitenanthrapi.service.ProjectService;
import com.example.multitenanthrapi.io.entity.Project;
import com.example.multitenanthrapi.io.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        Project e = new Project();
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
