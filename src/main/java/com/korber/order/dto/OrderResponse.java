package com.korber.order.dto;

import java.util.List;

public class OrderResponse {

    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private String status;
    private List<Long> reservedFromBatchIds;
    private String message;
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Long> getReservedFromBatchIds() {
		return reservedFromBatchIds;
	}
	public void setReservedFromBatchIds(List<Long> reservedFromBatchIds) {
		this.reservedFromBatchIds = reservedFromBatchIds;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}
