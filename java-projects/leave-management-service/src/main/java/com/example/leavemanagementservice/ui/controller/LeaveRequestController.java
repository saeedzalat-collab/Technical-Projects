package com.example.leavemanagementservice.ui.controller;

import com.example.leavemanagementservice.ui.model.RequestModel;
import com.example.leavemanagementservice.ui.model.ResponseModel;
import com.example.leavemanagementservice.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaverequests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private LeaveRequestService service;

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
