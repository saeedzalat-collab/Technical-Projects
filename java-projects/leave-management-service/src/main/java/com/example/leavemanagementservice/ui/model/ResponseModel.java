package com.example.leavemanagementservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseModel {
    private String id;
    private String message;
    private Object data;
    
    
	public ResponseModel(String id, String message, Object data) {
		super();
		this.id = id;
		this.message = message;
		this.data = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
    
}
