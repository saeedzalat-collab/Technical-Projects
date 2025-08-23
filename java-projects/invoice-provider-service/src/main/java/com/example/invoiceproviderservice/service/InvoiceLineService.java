package com.example.invoiceproviderservice.service;

import com.example.invoiceproviderservice.ui.model.RequestModel;
import com.example.invoiceproviderservice.ui.model.ResponseModel;

public interface InvoiceLineService {
    ResponseModel create(RequestModel req);
    ResponseModel get(String id);
    ResponseModel list();
}
