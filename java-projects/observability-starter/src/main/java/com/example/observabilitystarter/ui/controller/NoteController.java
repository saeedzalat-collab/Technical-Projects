package com.example.observabilitystarter.ui.controller;

import com.example.observabilitystarter.ui.model.RequestModel;
import com.example.observabilitystarter.ui.model.ResponseModel;
import com.example.observabilitystarter.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    @PostMapping
    public ResponseEntity<ResponseModel> create(@RequestBody RequestModel req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/<built-in function id>")
    public ResponseEntity<ResponseModel> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<ResponseModel> list() {
        return ResponseEntity.ok(service.list());
    }
}
