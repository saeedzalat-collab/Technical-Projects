package com.example.multitenanthrapi.service.impl;

import com.example.multitenanthrapi.ui.model.RequestModel;
import com.example.multitenanthrapi.ui.model.ResponseModel;
import com.example.multitenanthrapi.service.EmployeeService;
import com.example.multitenanthrapi.io.entity.Employee;
import com.example.multitenanthrapi.io.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        Employee e = new Employee();
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
