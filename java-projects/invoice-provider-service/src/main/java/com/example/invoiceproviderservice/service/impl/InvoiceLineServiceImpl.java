package com.example.invoiceproviderservice.service.impl;

import com.example.invoiceproviderservice.ui.model.RequestModel;
import com.example.invoiceproviderservice.ui.model.ResponseModel;
import com.example.invoiceproviderservice.service.InvoiceLineService;
import com.example.invoiceproviderservice.io.entity.InvoiceLine;
import com.example.invoiceproviderservice.io.repository.InvoiceLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceLineServiceImpl implements InvoiceLineService {
    private final InvoiceLineRepository repo;

    @Override
    public ResponseModel create(RequestModel req) {
        InvoiceLine e = new InvoiceLine();
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
