package com.example.invoiceproviderservice.ui.controller;

import com.example.invoiceproviderservice.ui.model.RequestModel;
import com.example.invoiceproviderservice.ui.model.ResponseModel;
import com.example.invoiceproviderservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService service;

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
