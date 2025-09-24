package com.appsdeveloperblog.app.ws.service;

import java.time.LocalDateTime;
import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.InvoiceDto;
import com.appsdeveloperblog.app.ws.shared.dto.InvoiceLineDto;
import com.appsdeveloperblog.app.ws.ui.model.request.InvoiceLineDetailsRequestModel;



public interface InvoiceService {
    InvoiceDto createInvoice(InvoiceDto invoice);
    
    InvoiceDto getInvoiceById(String invoiceId);

    List<InvoiceDto> getInvoicesByProvider(String providerName);

    List<InvoiceDto> getInvoicesByDate(String date);
    
    InvoiceLineDto getInvoiceLineById(String invoiceId, String lineId);

    InvoiceDto updateInvoice(String invoiceId, InvoiceDto invoiceDto);

    InvoiceLineDto updateInvoiceLine(String invoiceId, String lineId, InvoiceLineDetailsRequestModel updateModel);
    
    void deleteInvoice(String invoiceId);
    
    void deleteInvoiceLine(String invoiceId, String lineId);






}
