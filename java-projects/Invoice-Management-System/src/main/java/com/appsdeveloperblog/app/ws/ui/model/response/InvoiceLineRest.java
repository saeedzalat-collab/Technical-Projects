package com.appsdeveloperblog.app.ws.ui.model.response;

public class InvoiceLineRest {

	private String productName;
	private int quantity;
	private double price;
	private double lineValue;

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


}
