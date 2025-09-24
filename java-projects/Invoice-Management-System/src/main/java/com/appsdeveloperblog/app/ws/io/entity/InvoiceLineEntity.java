package com.appsdeveloperblog.app.ws.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_lines")
public class InvoiceLineEntity {
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String invoiceLineId;
	
	@Column(nullable = false)
    private String productName;
	
	@Column(nullable = false)
    private int quantity;
	
	@Column(nullable = false)
    private double price;
	
	@Column(nullable = false)
    private double lineValue;

    @ManyToOne 
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceLineId() {
		return invoiceLineId;
	}

	public void setInvoiceLineId(String invoiceLineId) {
		this.invoiceLineId = invoiceLineId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}



	public double getLineValue() {
		return lineValue;
	}

	public void setLineValue(double lineValue) {
		this.lineValue = lineValue;
	}

	public InvoiceEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}
    
    
}
