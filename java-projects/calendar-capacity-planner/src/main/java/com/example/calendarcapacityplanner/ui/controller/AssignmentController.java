package com.example.calendarcapacityplanner.ui.controller;

import com.example.calendarcapacityplanner.ui.model.RequestModel;
import com.example.calendarcapacityplanner.ui.model.ResponseModel;
import com.example.calendarcapacityplanner.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService service;

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
