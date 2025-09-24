package com.appsdeveloperblog.app.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDto implements Serializable {
	private static final long serialVersionUID = -2427963893823935805L;

	private long id;
	private String InvoiceId;
	private LocalDateTime dateTime;
	private String providerName;
	private String address;
	private double total;
	private double paid;
	private double remaining;
	private String deliveredBy;
	private List<InvoiceLineDto> invoiceLines;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInvoiceId() {
		return InvoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		InvoiceId = invoiceId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getRemaining() {
		return remaining;
	}

	public void setRemaining(double remaining) {
		this.remaining = remaining;
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

}
