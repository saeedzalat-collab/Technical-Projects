package com.example.multitenanthrapi.ui.controller;

import com.example.multitenanthrapi.ui.model.RequestModel;
import com.example.multitenanthrapi.ui.model.ResponseModel;
import com.example.multitenanthrapi.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {
    private final WorkLogService service;

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
