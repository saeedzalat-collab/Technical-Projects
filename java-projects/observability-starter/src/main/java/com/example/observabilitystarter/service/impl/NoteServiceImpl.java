package com.example.observabilitystarter.service.impl;

import com.example.observabilitystarter.ui.model.RequestModel;
import com.example.observabilitystarter.ui.model.ResponseModel;
import com.example.observabilitystarter.service.NoteService;
import com.example.observabilitystarter.io.entity.Note;
import com.example.observabilitystarter.io.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        Note e = new Note();
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
