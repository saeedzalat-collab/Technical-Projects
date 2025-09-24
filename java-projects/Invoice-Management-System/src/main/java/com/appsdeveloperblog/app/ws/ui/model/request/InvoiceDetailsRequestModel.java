package com.appsdeveloperblog.app.ws.ui.model.request;

import java.time.LocalDateTime;
import java.util.List;

import com.appsdeveloperblog.app.ws.shared.dto.InvoiceLineDto;

public class InvoiceDetailsRequestModel {

	//private LocalDateTime dateTime;
	private String providerName;
	//private String address;
	//private double total;
	private double paid;
	//private double remaining;
	private String deliveredBy;
	private List<InvoiceLineDto> invoiceLines;

	public double getPaid() {
		return paid;
	}
	public void setPaid(double paid) {
		this.paid = paid;
	}
	public String getDeliveredBy() {
		return deliveredBy;
	}
	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}
	public List<InvoiceLineDto> getInvoiceLines() {
		return invoiceLines;
	}
	public void setInvoiceLines(List<InvoiceLineDto> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	



}
